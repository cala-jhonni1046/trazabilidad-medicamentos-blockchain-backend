package ies.belgrano.medicamentos.dispensacion;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.cuarentena.CuarentenaRepository;
import ies.belgrano.medicamentos.cuarentena.EstadoCuarentena;
import ies.belgrano.medicamentos.despachologistico.EstadoDespacho;
import ies.belgrano.medicamentos.enlaceCUIT.EstadoEnlaceCuit;
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.lote.EstadoLote;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.lote.LoteRepository;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.organizacion.OrganizacionRepository;
import ies.belgrano.medicamentos.organizacion.TipoOrganizacion;
import ies.belgrano.medicamentos.recepcionfarmacia.EstadoRecepcion;
import ies.belgrano.medicamentos.recepcionfarmacia.RecepcionFarmacia;
import ies.belgrano.medicamentos.recepcionfarmacia.RecepcionFarmaciaRepository;
import ies.belgrano.medicamentos.unidadTrazable.EstadoUnidadTrazable;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazableRepository;

@Service
public class DispensacionService {

    private final DispensacionRepository dispensacionRepo;
    private final UnidadTrazableRepository unidadRepo;
    private final OrganizacionRepository organizacionRepo;
    private final RecepcionFarmaciaRepository recepcionRepo;
    private final CuarentenaRepository cuarentenaRepo;
    private final LoteRepository loteRepo;

    public DispensacionService(DispensacionRepository dispensacionRepo,
                               UnidadTrazableRepository unidadRepo,
                               OrganizacionRepository organizacionRepo,
                               RecepcionFarmaciaRepository recepcionRepo,
                               CuarentenaRepository cuarentenaRepo,
                               LoteRepository loteRepo) {
        this.dispensacionRepo = dispensacionRepo;
        this.unidadRepo = unidadRepo;
        this.organizacionRepo = organizacionRepo;
        this.recepcionRepo = recepcionRepo;
        this.cuarentenaRepo = cuarentenaRepo;
        this.loteRepo = loteRepo;
    }

    @Transactional(readOnly = true)
    public List<Dispensacion> getAll(Long farmaciaId, Long unidadTrazableId) {
        if (farmaciaId != null && unidadTrazableId != null) {
            return dispensacionRepo.findByFarmaciaIdAndUnidadTrazableId(farmaciaId, unidadTrazableId);
        }
        if (unidadTrazableId != null) {
            return dispensacionRepo.findByUnidadTrazableId(unidadTrazableId)
                    .map(List::of)
                    .orElse(Collections.emptyList());
        }
        if (farmaciaId != null) {
            if (!organizacionRepo.existsById(farmaciaId)) {
                throw new ResourceNotFoundException("Farmacia no encontrada con ID: " + farmaciaId);
            }
            return dispensacionRepo.findByFarmaciaId(farmaciaId);
        }
        return dispensacionRepo.findAllByOrderByFechaDispensacionDescIdDesc();
    }

    @Transactional(readOnly = true)
    public List<Dispensacion> getAll() {
        return getAll(null, null);
    }

    @Transactional(readOnly = true)
    public Dispensacion getById(Long id) {
        return dispensacionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispensación no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public Dispensacion getByUnidadTrazableId(Long unidadTrazableId) {
        if (unidadTrazableId == null) {
            throw new IllegalArgumentException("El ID de unidad trazable es obligatorio.");
        }
        return dispensacionRepo.findByUnidadTrazableId(unidadTrazableId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró ninguna dispensación registrada para la unidad trazable con ID: " + unidadTrazableId));
    }

    @Transactional
    public Dispensacion dispensar(DispensacionRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos de la dispensación son obligatorios.");
        }

        // 1. Validar campos requeridos
        if (dto.getUnidadTrazableId() == null) {
            throw new IllegalArgumentException("El ID de unidad trazable es obligatorio.");
        }
        if (dto.getFarmaciaId() == null) {
            throw new IllegalArgumentException("El ID de farmacia es obligatorio.");
        }

        // 2. Regla 1 de Elegibilidad: La unidad existe y está DISPONIBLE
        UnidadTrazable unidad = unidadRepo.findById(dto.getUnidadTrazableId())
                .orElseThrow(() -> new ResourceNotFoundException("Unidad trazable no encontrada con ID: " + dto.getUnidadTrazableId()));

        if (unidad.getEstado() != EstadoUnidadTrazable.DISPONIBLE) {
            throw new IllegalStateException("La unidad trazable con ID " + unidad.getId()
                    + " no se encuentra DISPONIBLE para dispensación (estado actual: " + unidad.getEstado() + ").");
        }

        // 3. Regla 5 de Elegibilidad: No existe otra dispensación para la unidad
        if (dispensacionRepo.existsByUnidadTrazableId(unidad.getId()) || unidad.getDispensacion() != null) {
            throw new DataIntegrityViolationException("Ya existe una dispensación registrada para la unidad trazable con ID: " + unidad.getId());
        }

        // 4. Regla 2 de Elegibilidad: Su lote existe y no está en cuarentena ni en estado no dispensable
        Lote lote = unidad.getLote();
        if (lote == null || lote.getId() == null) {
            throw new IllegalStateException("La unidad trazable con ID " + unidad.getId() + " no posee un lote asociado.");
        }
        Lote loteEntidad = loteRepo.findById(lote.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado con ID: " + lote.getId()));

        if (loteEntidad.getEstado() == EstadoLote.EN_CUARENTENA) {
            throw new IllegalStateException("No es posible dispensar la unidad porque el lote "
                    + loteEntidad.getCodigoLote() + " se encuentra EN_CUARENTENA.");
        }

        if (loteEntidad.getEstado() == EstadoLote.CERRADO) {
            throw new IllegalStateException("No es posible dispensar la unidad porque el lote "
                    + loteEntidad.getCodigoLote() + " se encuentra en estado terminal CERRADO.");
        }

        if (cuarentenaRepo.existsByLoteIdAndEstado(loteEntidad.getId(), EstadoCuarentena.ABIERTA)) {
            throw new IllegalStateException("No es posible dispensar la unidad porque el lote "
                    + loteEntidad.getCodigoLote() + " registra una cuarentena en estado ABIERTA.");
        }

        if (loteEntidad.getEstado() != EstadoLote.RECIBIDO && loteEntidad.getEstado() != EstadoLote.LIBERADO) {
            throw new IllegalStateException("No es posible dispensar la unidad porque el lote "
                    + loteEntidad.getCodigoLote() + " no se encuentra en estado RECIBIDO ni LIBERADO (estado actual: "
                    + loteEntidad.getEstado() + ").");
        }

        // 5. Regla 3 de Elegibilidad: La farmacia declarada existe, es tipo FARMACIA
        Organizacion farmacia = organizacionRepo.findById(dto.getFarmaciaId())
                .orElseThrow(() -> new ResourceNotFoundException("Farmacia no encontrada con ID: " + dto.getFarmaciaId()));

        if (farmacia.getTipo() != TipoOrganizacion.FARMACIA) {
            throw new IllegalArgumentException("La organización indicada con ID " + dto.getFarmaciaId()
                    + " no es de tipo FARMACIA (tipo actual: " + farmacia.getTipo() + ").");
        }

        // 6. Regla 4 de Elegibilidad: El lote fue recibido en esa farmacia conforme a B06
        // Se exige una recepción CONFORME para este lote en esta farmacia,
        // con despacho RECIBIDO y enlace CUIT ACTIVO destinado a esta farmacia.
        List<RecepcionFarmacia> recepciones = recepcionRepo.findByDespachoLogisticoLoteIdAndFarmaciaId(loteEntidad.getId(), farmacia.getId());

        boolean recepcionValida = recepciones.stream().anyMatch(r ->
                r.getEstadoConformidad() == EstadoRecepcion.CONFORME
                && r.getDespachoLogistico() != null
                && r.getDespachoLogistico().getEstado() == EstadoDespacho.RECIBIDO
                && r.getDespachoLogistico().getEnlaceCuit() != null
                && r.getDespachoLogistico().getEnlaceCuit().getEstado() == EstadoEnlaceCuit.ACTIVO
                && r.getDespachoLogistico().getEnlaceCuit().getFarmacia() != null
                && r.getDespachoLogistico().getEnlaceCuit().getFarmacia().getId().equals(farmacia.getId())
        );

        if (!recepcionValida) {
            throw new IllegalStateException("No es posible dispensar la unidad: el lote "
                    + loteEntidad.getCodigoLote() + " no cuenta con una recepción CONFORME en la farmacia "
                    + farmacia.getNombre() + " (ID " + farmacia.getId() + ") conforme a B06.");
        }

        // 7. Transición atómica: actualizar estado de unidad y persistir la dispensación
        LocalDateTime fecha = dto.getFechaDispensacion() != null
                ? dto.getFechaDispensacion()
                : LocalDateTime.now(ZoneOffset.UTC);

        unidad.setEstado(EstadoUnidadTrazable.DISPENSADA);
        unidadRepo.save(unidad);

        Dispensacion dispensacion = new Dispensacion();
        dispensacion.setUnidadTrazable(unidad);
        dispensacion.setFarmacia(farmacia);
        dispensacion.setFechaDispensacion(fecha);
        dispensacion.setRecetaNumero(dto.getRecetaNumero() != null ? dto.getRecetaNumero().trim() : null);
        dispensacion.setDniPaciente(dto.getDniPaciente() != null ? dto.getDniPaciente().trim() : null);

        return dispensacionRepo.save(dispensacion);
    }

    @Transactional
    public Dispensacion update(Long id, DispensacionRequestDTO dto) {
        getById(id);
        throw new IllegalStateException("No está permitido modificar una dispensación registrada.");
    }

    @Transactional
    public void delete(Long id) {
        getById(id);
        throw new IllegalStateException("No está permitido eliminar registros de dispensación para preservar el registro y trazabilidad legal.");
    }
}
