package ies.belgrano.medicamentos.recepcionfarmacia;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import ies.belgrano.medicamentos.security.AccesoOrganizacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.despachologistico.DespachoLogisticoRepository;
import ies.belgrano.medicamentos.despachologistico.DespachoLogisticoService;
import ies.belgrano.medicamentos.despachologistico.EstadoDespacho;
import ies.belgrano.medicamentos.despachologistico.TipoEventoCustodia;
import ies.belgrano.medicamentos.enlaceCUIT.EnlaceCUIT;
import ies.belgrano.medicamentos.enlaceCUIT.EstadoEnlaceCuit;
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.lote.EstadoLote;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.lote.LoteRepository;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.organizacion.OrganizacionRepository;
import ies.belgrano.medicamentos.organizacion.TipoOrganizacion;

@Service
public class RecepcionFarmaciaService {

    private final RecepcionFarmaciaRepository recepcionRepo;
    private final DespachoLogisticoRepository despachoRepo;
    private final OrganizacionRepository organizacionRepo;
    private final LoteRepository loteRepo;
    private final DespachoLogisticoService despachoService;
    private final AccesoOrganizacion acceso;

    public RecepcionFarmaciaService(RecepcionFarmaciaRepository recepcionRepo,
                                   DespachoLogisticoRepository despachoRepo,
                                   OrganizacionRepository organizacionRepo,
                                   LoteRepository loteRepo,
                                   DespachoLogisticoService despachoService,
                                   AccesoOrganizacion acceso) {
        this.recepcionRepo = recepcionRepo;
        this.despachoRepo = despachoRepo;
        this.organizacionRepo = organizacionRepo;
        this.loteRepo = loteRepo;
        this.despachoService = despachoService;
        this.acceso = acceso;
    }

    @Transactional(readOnly = true)
    public List<RecepcionFarmacia> getAll(Long despachoLogisticoId, Long farmaciaId, EstadoRecepcion estadoConformidad) {
        if (!acceso.esAdminOGlobal()) {
            // Empresas: la farmacia ve sus recepciones; laboratorio y distribuidor, las de sus despachos
            Long orgId = acceso.organizacionActualId();
            if (orgId == null) {
                return List.of();
            }
            return recepcionRepo.findVisiblesPorOrganizacion(orgId, despachoLogisticoId, farmaciaId, estadoConformidad);
        }
        if (despachoLogisticoId != null && !despachoRepo.existsById(despachoLogisticoId)) {
            throw new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + despachoLogisticoId);
        }
        if (farmaciaId != null && !organizacionRepo.existsById(farmaciaId)) {
            throw new ResourceNotFoundException("Farmacia no encontrada con ID: " + farmaciaId);
        }
        if (despachoLogisticoId == null && farmaciaId == null && estadoConformidad == null) {
            return recepcionRepo.findAllByOrderByFechaHoraRecepcionDescIdDesc();
        }
        return recepcionRepo.findByFiltros(despachoLogisticoId, farmaciaId, estadoConformidad);
    }

    @Transactional(readOnly = true)
    public List<RecepcionFarmacia> getAll() {
        return getAll(null, null, null);
    }

    @Transactional(readOnly = true)
    public RecepcionFarmacia getById(Long id) {
        RecepcionFarmacia recepcion = recepcionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recepción de farmacia no encontrada con ID: " + id));
        DespachoLogistico d = recepcion.getDespachoLogistico();
        Long labId = d != null && d.getLote() != null && d.getLote().getLaboratorio() != null
                ? d.getLote().getLaboratorio().getId() : null;
        Long distId = d != null && d.getDistribuidor() != null ? d.getDistribuidor().getId() : null;
        // 404 si la organización no es la farmacia receptora ni el laboratorio/distribuidor del despacho
        acceso.verificarAccesoMultiple(recepcion.getFarmacia().getId(), labId, distId);
        return recepcion;
    }

    /** Escritura: solo la farmacia que registró la recepción (o SUPERADMIN). */
    private RecepcionFarmacia getPropia(Long id) {
        RecepcionFarmacia recepcion = getById(id);
        acceso.verificarAcceso(recepcion.getFarmacia().getId());
        return recepcion;
    }

    @Transactional
    public RecepcionFarmacia create(RecepcionFarmaciaRequestDTO dto) {
        String comprobante = dto.getNumeroComprobante() != null ? dto.getNumeroComprobante().trim() : null;
        if (comprobante == null || comprobante.isBlank()) {
            throw new IllegalArgumentException("El número de comprobante es obligatorio.");
        }
        if (recepcionRepo.existsByNumeroComprobante(comprobante)) {
            throw new DataIntegrityViolationException("Ya existe una recepción registrada con el número de comprobante: " + comprobante);
        }

        if (dto.getDespachoLogisticoId() == null) {
            throw new IllegalArgumentException("El ID de despacho logístico es obligatorio.");
        }
        DespachoLogistico despacho = despachoRepo.findById(dto.getDespachoLogisticoId())
                .orElseThrow(() -> new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + dto.getDespachoLogisticoId()));

        // Solo la farmacia de destino del despacho puede registrar su recepción (404 para las demás)
        if (despacho.getEnlaceCuit() != null && despacho.getEnlaceCuit().getFarmacia() != null) {
            acceso.verificarAcceso(despacho.getEnlaceCuit().getFarmacia().getId());
        }

        if (recepcionRepo.existsByDespachoLogisticoId(despacho.getId())) {
            throw new DataIntegrityViolationException("Ya existe una recepción registrada para el despacho logístico con ID: " + despacho.getId());
        }

        if (despacho.getEstado() != EstadoDespacho.EN_TRANSITO) {
            throw new IllegalStateException("Solo se puede registrar la recepción de un despacho en estado EN_TRANSITO. Estado actual: " + despacho.getEstado());
        }

        if (despacho.getLote() != null && despacho.getLote().getEstado() == EstadoLote.EN_CUARENTENA) {
            throw new IllegalStateException("No es posible registrar la recepción en farmacia porque el lote "
                    + despacho.getLote().getCodigoLote() + " se encuentra EN_CUARENTENA.");
        }

        EnlaceCUIT enlace = despacho.getEnlaceCuit();
        if (enlace == null || enlace.getEstado() != EstadoEnlaceCuit.ACTIVO) {
            String estadoEnlace = (enlace != null && enlace.getEstado() != null) ? enlace.getEstado().name() : "INEXISTENTE";
            throw new IllegalStateException("El enlace CUIT asociado al despacho debe encontrarse en estado ACTIVO. Estado actual: " + estadoEnlace);
        }

        // La farmacia receptora sale del token; se ignora el farmaciaId del pedido (solo SUPERADMIN lo indica)
        Long farmaciaIdReceptora = acceso.organizacionObligatoria();
        if (farmaciaIdReceptora == null) {
            farmaciaIdReceptora = dto.getFarmaciaId();
        }
        if (farmaciaIdReceptora == null) {
            throw new IllegalArgumentException("El ID de farmacia es obligatorio.");
        }
        final Long farmId = farmaciaIdReceptora;
        Organizacion farmacia = organizacionRepo.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmacia no encontrada con ID: " + farmId));

        if (farmacia.getTipo() != TipoOrganizacion.FARMACIA) {
            throw new IllegalArgumentException("La organización indicada con ID " + farmId
                    + " no es de tipo FARMACIA (tipo actual: " + farmacia.getTipo() + ").");
        }

        if (enlace.getFarmacia() == null || !enlace.getFarmacia().getId().equals(farmacia.getId())) {
            Long enlaceFarmId = enlace.getFarmacia() != null ? enlace.getFarmacia().getId() : null;
            throw new IllegalStateException("La farmacia receptora (ID " + farmacia.getId()
                    + ") no coincide con la farmacia de destino prevista en el enlace CUIT activo (ID " + enlaceFarmId + ").");
        }

        if (dto.getEstadoConformidad() == null) {
            throw new IllegalArgumentException("El estado de conformidad es obligatorio.");
        }

        if (dto.getCadenaFrioIntacta() == null) {
            throw new IllegalArgumentException("Debe indicar si la cadena de frío se encuentra intacta.");
        }

        if (Boolean.FALSE.equals(dto.getCadenaFrioIntacta()) && dto.getEstadoConformidad() == EstadoRecepcion.CONFORME) {
            throw new IllegalArgumentException("No es posible registrar una recepción CONFORME si la cadena de frío no se encuentra intacta. El resultado debe ser OBSERVADA o RECHAZADA.");
        }

        LocalDateTime fechaHora = dto.getFechaHoraRecepcion() != null
                ? dto.getFechaHoraRecepcion()
                : LocalDateTime.now(ZoneOffset.UTC);

        // Transiciones coherentes y atómicas de Despacho y Lote según el resultado de conformidad
        EstadoRecepcion resultado = dto.getEstadoConformidad();
        if (resultado == EstadoRecepcion.CONFORME) {
            despacho.setEstado(EstadoDespacho.RECIBIDO);
            despacho.setFechaEntregaReal(fechaHora);
            despachoRepo.save(despacho);

            Lote lote = despacho.getLote();
            if (lote != null) {
                lote.setEstado(EstadoLote.RECIBIDO);
                loteRepo.save(lote);
            }

            String desc = (dto.getObservaciones() != null && !dto.getObservaciones().isBlank())
                    ? dto.getObservaciones().trim()
                    : "Recepción conforme confirmada por farmacia de destino.";
            despachoService.registrarEventoInterno(despacho, TipoEventoCustodia.RECEPCION_CONFIRMADA, fechaHora, farmacia, desc);

        } else if (resultado == EstadoRecepcion.OBSERVADA) {
            despacho.setEstado(EstadoDespacho.RECIBIDO);
            despacho.setFechaEntregaReal(fechaHora);
            despachoRepo.save(despacho);

            Lote lote = despacho.getLote();
            if (lote != null) {
                lote.setEstado(EstadoLote.RECIBIDO);
                loteRepo.save(lote);
            }

            String obs = (dto.getObservaciones() != null && !dto.getObservaciones().isBlank())
                    ? dto.getObservaciones().trim()
                    : "Recepción con observaciones registradas en farmacia.";
            String desc = "Recepción observada en farmacia: " + obs;
            despachoService.registrarEventoInterno(despacho, TipoEventoCustodia.RECEPCION_CONFIRMADA, fechaHora, farmacia, desc);

        } else if (resultado == EstadoRecepcion.RECHAZADA) {
            despacho.setEstado(EstadoDespacho.CANCELADO);
            despacho.setObservaciones("Recepción rechazada en farmacia de destino.");
            despachoRepo.save(despacho);
            // El lote no cambia de estado (queda en su estado previo)
            String motivo = dto.getObservaciones() != null && !dto.getObservaciones().isBlank()
                    ? dto.getObservaciones().trim()
                    : "Recepción rechazada sin motivo informado.";
            despachoService.registrarEventoInterno(despacho, TipoEventoCustodia.RECEPCION_RECHAZADA,
                    fechaHora, farmacia, motivo);
        }

        RecepcionFarmacia recepcion = new RecepcionFarmacia();
        recepcion.setNumeroComprobante(comprobante);
        recepcion.setFechaHoraRecepcion(fechaHora);
        recepcion.setFarmacia(farmacia);
        recepcion.setFarmaceuticoResponsable(dto.getFarmaceuticoResponsable() != null ? dto.getFarmaceuticoResponsable().trim() : null);
        recepcion.setDespachoLogistico(despacho);
        recepcion.setEstadoConformidad(resultado);
        recepcion.setCadenaFrioIntacta(dto.getCadenaFrioIntacta());
        recepcion.setObservaciones(dto.getObservaciones() != null ? dto.getObservaciones().trim() : null);

        return recepcionRepo.save(recepcion);
    }

    @Transactional
    public RecepcionFarmacia update(Long id, RecepcionFarmaciaRequestDTO dto) {
        RecepcionFarmacia existente = getPropia(id);

        if (dto.getDespachoLogisticoId() != null && existente.getDespachoLogistico() != null
                && !dto.getDespachoLogisticoId().equals(existente.getDespachoLogistico().getId())) {
            throw new IllegalStateException("No está permitido modificar el despacho logístico de una recepción ya registrada.");
        }

        if (dto.getFarmaciaId() != null && existente.getFarmacia() != null
                && !dto.getFarmaciaId().equals(existente.getFarmacia().getId())) {
            throw new IllegalStateException("No está permitido modificar la farmacia de una recepción ya registrada.");
        }

        if (dto.getEstadoConformidad() != null && dto.getEstadoConformidad() != existente.getEstadoConformidad()) {
            throw new IllegalStateException("No está permitido modificar el resultado de conformidad de una recepción ya registrada.");
        }

        if (dto.getCadenaFrioIntacta() != null && !dto.getCadenaFrioIntacta().equals(existente.getCadenaFrioIntacta())) {
            throw new IllegalStateException("No está permitido modificar la constancia de cadena de frío de una recepción ya registrada.");
        }

        String comprobante = dto.getNumeroComprobante() != null ? dto.getNumeroComprobante().trim() : null;
        if (comprobante != null && !comprobante.isBlank() && !comprobante.equals(existente.getNumeroComprobante())) {
            if (recepcionRepo.existsByNumeroComprobanteAndIdNot(comprobante, id)) {
                throw new DataIntegrityViolationException("Ya existe otra recepción registrada con el número de comprobante: " + comprobante);
            }
            existente.setNumeroComprobante(comprobante);
        }

        if (dto.getFarmaceuticoResponsable() != null) {
            existente.setFarmaceuticoResponsable(dto.getFarmaceuticoResponsable().trim());
        }

        if (dto.getObservaciones() != null) {
            existente.setObservaciones(dto.getObservaciones().trim());
        }

        return recepcionRepo.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        // Valida existencia y pertenencia
        getPropia(id);
        throw new IllegalStateException("No está permitido eliminar recepciones de farmacia para preservar la integridad y trazabilidad del despacho.");
    }
}
