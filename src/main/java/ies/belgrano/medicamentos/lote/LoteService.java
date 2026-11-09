package ies.belgrano.medicamentos.lote;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.despachologistico.DespachoLogisticoRepository;
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.medicamento.Medicamento;
import ies.belgrano.medicamentos.medicamento.MedicamentoRepository;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.organizacion.OrganizacionRepository;
import ies.belgrano.medicamentos.organizacion.TipoOrganizacion;
import ies.belgrano.medicamentos.security.AccesoOrganizacion;
import ies.belgrano.medicamentos.unidadTrazable.EstadoUnidadTrazable;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazableRepository;

@Service
public class LoteService {

    private final LoteRepository loteRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final OrganizacionRepository organizacionRepository;
    private final UnidadTrazableRepository unidadTrazableRepository;
    private final DespachoLogisticoRepository despachoLogisticoRepository;
    private final AccesoOrganizacion acceso;

    public LoteService(LoteRepository loteRepository,
                       MedicamentoRepository medicamentoRepository,
                       OrganizacionRepository organizacionRepository,
                       UnidadTrazableRepository unidadTrazableRepository,
                       DespachoLogisticoRepository despachoLogisticoRepository,
                       AccesoOrganizacion acceso) {
        this.loteRepository = loteRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.organizacionRepository = organizacionRepository;
        this.unidadTrazableRepository = unidadTrazableRepository;
        this.despachoLogisticoRepository = despachoLogisticoRepository;
        this.acceso = acceso;
    }

    @Transactional(readOnly = true)
    public List<Lote> getAll() {
        return getAll(null, null, null);
    }

    @Transactional(readOnly = true)
    public List<Lote> getAll(Long medicamentoId, Long laboratorioId, EstadoLote estado) {
        if (medicamentoId != null && !medicamentoRepository.existsById(medicamentoId)) {
            throw new ResourceNotFoundException("Medicamento no encontrado con ID: " + medicamentoId);
        }
        if (laboratorioId != null && !organizacionRepository.existsById(laboratorioId)) {
            throw new ResourceNotFoundException("Laboratorio no encontrado con ID: " + laboratorioId);
        }
        if (!acceso.esAdminOGlobal()) {
            // Empresas: solo sus lotes y los de los despachos en los que participan
            Long orgId = acceso.organizacionActualId();
            if (orgId == null) {
                return List.of();
            }
            return loteRepository.findVisiblesPorOrganizacion(orgId, medicamentoId, laboratorioId, estado);
        }
        if (medicamentoId == null && laboratorioId == null && estado == null) {
            return loteRepository.findAll();
        }
        return loteRepository.findByFiltros(medicamentoId, laboratorioId, estado);
    }

    @Transactional(readOnly = true)
    public List<Lote> getByMedicamentoId(Long medicamentoId) {
        return getAll(medicamentoId, null, null);
    }

    /** Lectura: el lote debe ser visible para la organización del usuario (404 si no). */
    @Transactional(readOnly = true)
    public Lote getById(Long id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado con ID: " + id));
        if (!acceso.esAdminOGlobal()) {
            Long orgId = acceso.organizacionActualId();
            if (orgId == null || !loteRepository.esVisiblePorOrganizacion(id, orgId)) {
                throw new ResourceNotFoundException("Lote no encontrado con ID: " + id);
            }
        }
        return lote;
    }

    /** Escritura: solo el laboratorio dueño del lote (o SUPERADMIN). 404 si pertenece a otra organización. */
    private Lote getPropio(Long id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado con ID: " + id));
        acceso.verificarAcceso(lote.getLaboratorio().getId());
        return lote;
    }

    @Transactional
    public Lote create(LoteRequestDTO dto) {
        String codigoLote = dto.getCodigoLote() != null ? dto.getCodigoLote().trim() : null;
        if (codigoLote == null || codigoLote.isBlank()) {
            throw new IllegalArgumentException("El código de lote es obligatorio.");
        }

        if (loteRepository.existsByCodigoLote(codigoLote)) {
            throw new DataIntegrityViolationException("Ya existe un lote registrado con el código: " + codigoLote);
        }

        if (dto.getMedicamentoId() == null) {
            throw new IllegalArgumentException("El ID de medicamento es obligatorio.");
        }
        Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con ID: " + dto.getMedicamentoId()));

        // Un laboratorio solo crea lotes a su nombre: se ignora el laboratorioId del pedido.
        Long laboratorioId = acceso.organizacionObligatoria();
        if (laboratorioId == null) {
            laboratorioId = dto.getLaboratorioId(); // SUPERADMIN
        }
        if (laboratorioId == null) {
            throw new IllegalArgumentException("El ID de laboratorio es obligatorio.");
        }
        final Long labId = laboratorioId;
        Organizacion laboratorio = organizacionRepository.findById(labId)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratorio no encontrado con ID: " + labId));

        if (laboratorio.getTipo() != TipoOrganizacion.LABORATORIO) {
            throw new IllegalArgumentException("La organización indicada con ID " + labId + " no es de tipo LABORATORIO.");
        }

        if (dto.getTempMin() != null && dto.getTempMax() != null && dto.getTempMin() > dto.getTempMax()) {
            throw new IllegalArgumentException("La temperatura mínima no puede superar la temperatura máxima.");
        }

        Lote lote = new Lote();
        lote.setCodigoLote(codigoLote);
        lote.setMedicamento(medicamento);
        lote.setLaboratorio(laboratorio);
        lote.setTempMin(dto.getTempMin());
        lote.setTempMax(dto.getTempMax());
        lote.setFechaHoraFabricacion(dto.getFechaHoraFabricacion());
        lote.setEstado(EstadoLote.CREADO);

        return loteRepository.save(lote);
    }

    @Transactional
    public Lote update(Long id, LoteRequestDTO dto) {
        Lote existente = getPropio(id);

        String nuevoCodigo = dto.getCodigoLote() != null ? dto.getCodigoLote().trim() : null;
        if (nuevoCodigo == null || nuevoCodigo.isBlank()) {
            throw new IllegalArgumentException("El código de lote es obligatorio.");
        }
        if (!nuevoCodigo.equals(existente.getCodigoLote())) {
            if (loteRepository.existsByCodigoLoteAndIdNot(nuevoCodigo, id)) {
                throw new DataIntegrityViolationException("Ya existe otro lote registrado con el código: " + nuevoCodigo);
            }
            existente.setCodigoLote(nuevoCodigo);
        }

        if (dto.getMedicamentoId() != null && !dto.getMedicamentoId().equals(existente.getMedicamento().getId())) {
            Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con ID: " + dto.getMedicamentoId()));
            existente.setMedicamento(medicamento);
        }

        // Solo SUPERADMIN puede reasignar un lote a otro laboratorio
        if (acceso.esAdminOGlobal() && dto.getLaboratorioId() != null
                && !dto.getLaboratorioId().equals(existente.getLaboratorio().getId())) {
            Organizacion laboratorio = organizacionRepository.findById(dto.getLaboratorioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Laboratorio no encontrado con ID: " + dto.getLaboratorioId()));
            if (laboratorio.getTipo() != TipoOrganizacion.LABORATORIO) {
                throw new IllegalArgumentException("La organización indicada con ID " + dto.getLaboratorioId() + " no es de tipo LABORATORIO.");
            }
            existente.setLaboratorio(laboratorio);
        }

        if (dto.getTempMin() != null && dto.getTempMax() != null && dto.getTempMin() > dto.getTempMax()) {
            throw new IllegalArgumentException("La temperatura mínima no puede superar la temperatura máxima.");
        }

        existente.setTempMin(dto.getTempMin());
        existente.setTempMax(dto.getTempMax());
        existente.setFechaHoraFabricacion(dto.getFechaHoraFabricacion());

        // El estado no se modifica arbitrariamente a través de este CRUD
        return loteRepository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        Lote existente = getPropio(id);

        if (unidadTrazableRepository.existsByLoteId(id)) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el lote con ID " + id + " porque tiene unidades trazables asociadas.");
        }

        if (despachoLogisticoRepository.existsByLoteId(id)) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el lote con ID " + id + " porque tiene despachos logísticos asociados.");
        }

        try {
            loteRepository.delete(existente);
            loteRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el lote con ID " + id + " debido a dependencias con otros registros.");
        }
    }

    @Transactional
    public GeneracionUnidadesResponseDTO generarUnidades(Long id, GenerarUnidadesRequestDTO dto) {
        if (dto == null || dto.getCantidad() == null || dto.getCantidad() < 1 || dto.getCantidad() > 1000) {
            throw new IllegalArgumentException("La cantidad debe ser un número entero entre 1 y 1000.");
        }

        Lote lote = getPropio(id);

        if (lote.getEstado() == EstadoLote.CERRADO || lote.getEstado() == EstadoLote.EN_CUARENTENA) {
            throw new IllegalStateException("No se pueden generar unidades para un lote en estado " + lote.getEstado() + ".");
        }

        int cantidad = dto.getCantidad();
        String codigoLote = lote.getCodigoLote();
        String prefix = "QR-" + codigoLote + "-";

        List<UnidadTrazable> unidadesExistentes = unidadTrazableRepository.findByLoteId(id);
        int maxNumero = 0;
        for (UnidadTrazable u : unidadesExistentes) {
            String qr = u.getCodigoUnicoQR();
            if (qr != null && qr.startsWith(prefix)) {
                String sub = qr.substring(prefix.length());
                try {
                    int num = Integer.parseInt(sub);
                    if (num > maxNumero) {
                        maxNumero = num;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        if (maxNumero == 0 && !unidadesExistentes.isEmpty()) {
            maxNumero = unidadesExistentes.size();
        }

        List<String> codigos = new ArrayList<>(cantidad);
        for (int i = 1; i <= cantidad; i++) {
            int correlativo = maxNumero + i;
            codigos.add(String.format("QR-%s-%05d", codigoLote, correlativo));
        }

        // Validación de no colisión en una sola consulta para evitar N+1
        List<UnidadTrazable> colisiones = unidadTrazableRepository.findByCodigoUnicoQRIn(codigos);
        if (!colisiones.isEmpty()) {
            throw new DataIntegrityViolationException("Ya existe una unidad trazable registrada con el código: "
                    + colisiones.get(0).getCodigoUnicoQR());
        }

        List<UnidadTrazable> nuevasUnidades = new ArrayList<>(cantidad);
        for (String qr : codigos) {
            UnidadTrazable u = new UnidadTrazable();
            u.setLote(lote);
            u.setCodigoUnicoQR(qr);
            u.setEstado(EstadoUnidadTrazable.DISPONIBLE);
            nuevasUnidades.add(u);
        }

        unidadTrazableRepository.saveAll(nuevasUnidades);

        String primerCodigo = codigos.get(0);
        String ultimoCodigo = codigos.get(codigos.size() - 1);

        return new GeneracionUnidadesResponseDTO(lote.getId(), codigoLote, cantidad, primerCodigo, ultimoCodigo);
    }
}
