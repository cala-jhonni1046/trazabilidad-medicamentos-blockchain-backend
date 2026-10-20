package ies.belgrano.medicamentos.cuarentena;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.inspector.EstadoInspector;
import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.inspector.InspectorANMATRepository;
import ies.belgrano.medicamentos.lote.EstadoLote;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.lote.LoteRepository;

@Service
public class CuarentenaService {

    private final CuarentenaRepository repo;
    private final LoteRepository loteRepo;
    private final InspectorANMATRepository inspectorRepo;

    public CuarentenaService(CuarentenaRepository repo,
                             LoteRepository loteRepo,
                             InspectorANMATRepository inspectorRepo) {
        this.repo = repo;
        this.loteRepo = loteRepo;
        this.inspectorRepo = inspectorRepo;
    }

    @Transactional(readOnly = true)
    public List<Cuarentena> getAll(Long loteId, EstadoCuarentena estado) {
        if (loteId != null) {
            if (!loteRepo.existsById(loteId)) {
                throw new ResourceNotFoundException("Lote no encontrado con ID: " + loteId);
            }
            if (estado != null) {
                return repo.findByLoteIdAndEstado(loteId, estado);
            }
            return repo.findByLoteId(loteId);
        }
        if (estado != null) {
            return repo.findByEstado(estado);
        }
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cuarentena> getAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Cuarentena getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuarentena no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public Cuarentena getCuarentenaAbiertaPorLote(Long loteId) {
        if (loteId == null) {
            throw new IllegalArgumentException("El ID de lote es obligatorio.");
        }
        if (!loteRepo.existsById(loteId)) {
            throw new ResourceNotFoundException("Lote no encontrado con ID: " + loteId);
        }
        return repo.findFirstByLoteIdAndEstado(loteId, EstadoCuarentena.ABIERTA)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró ninguna cuarentena abierta para el lote con ID: " + loteId));
    }

    @Transactional
    public Cuarentena abrirCuarentena(CuarentenaRequestDTO dto) {
        String codigoCuarentena = dto.getCodigoCuarentena() != null ? dto.getCodigoCuarentena().trim() : null;
        if (codigoCuarentena == null || codigoCuarentena.isBlank()) {
            throw new IllegalArgumentException("El código de cuarentena es obligatorio.");
        }

        if (repo.existsByCodigoCuarentena(codigoCuarentena)) {
            throw new DataIntegrityViolationException("Ya existe una cuarentena registrada con el código: " + codigoCuarentena);
        }

        if (dto.getEstado() != null && dto.getEstado() != EstadoCuarentena.ABIERTA) {
            throw new IllegalArgumentException("Una cuarentena no puede crearse directamente resuelta. Debe nacer en estado ABIERTA.");
        }

        if (dto.getLoteId() == null) {
            throw new IllegalArgumentException("El ID de lote es obligatorio.");
        }

        Lote lote = loteRepo.findByIdWithLock(dto.getLoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado con ID: " + dto.getLoteId()));

        if (lote.getEstado() == EstadoLote.CERRADO) {
            throw new IllegalStateException("No se puede poner en cuarentena un lote en estado terminal CERRADO.");
        }

        if (repo.existsByLoteIdAndEstado(lote.getId(), EstadoCuarentena.ABIERTA)) {
            throw new IllegalStateException("El lote " + lote.getCodigoLote() + " (ID " + lote.getId()
                    + ") ya cuenta con una cuarentena en estado ABIERTA.");
        }

        if (dto.getMotivo() == null || dto.getMotivo().isBlank()) {
            throw new IllegalArgumentException("El motivo de cuarentena es obligatorio.");
        }

        InspectorANMAT inspector = null;
        if (dto.getInspectorResponsableId() != null) {
            inspector = inspectorRepo.findById(dto.getInspectorResponsableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inspector ANMAT no encontrado con ID: " + dto.getInspectorResponsableId()));

            if (inspector.getEstado() != EstadoInspector.HABILITADO) {
                throw new IllegalStateException("El inspector ANMAT con ID " + inspector.getId()
                        + " no se encuentra en estado HABILITADO (estado actual: " + inspector.getEstado() + ").");
            }
        }

        LocalDateTime fechaInicio = dto.getFechaInicio() != null
                ? dto.getFechaInicio()
                : LocalDateTime.now(ZoneOffset.UTC);

        Cuarentena cuarentena = new Cuarentena();
        cuarentena.setCodigoCuarentena(codigoCuarentena);
        cuarentena.setFechaInicio(fechaInicio);
        cuarentena.setFechaFin(null);
        cuarentena.setMotivo(dto.getMotivo().trim());
        cuarentena.setEstado(EstadoCuarentena.ABIERTA);
        cuarentena.setLote(lote);
        cuarentena.setInspectorResponsable(inspector);
        cuarentena.setResolucionFinal(null);
        cuarentena.setObservaciones(dto.getObservaciones() != null ? dto.getObservaciones().trim() : null);

        lote.setEstado(EstadoLote.EN_CUARENTENA);
        loteRepo.save(lote);

        return repo.save(cuarentena);
    }

    @Transactional
    public Cuarentena resolverCuarentena(Long id, CuarentenaResolucionRequestDTO dto) {
        Cuarentena existente = getById(id);

        if (existente.getEstado() != EstadoCuarentena.ABIERTA) {
            throw new IllegalStateException("Solo se puede resolver una cuarentena en estado ABIERTA. Estado actual: "
                    + existente.getEstado());
        }

        if (dto == null || dto.getEstado() == null || dto.getEstado() == EstadoCuarentena.ABIERTA) {
            throw new IllegalArgumentException("El estado de resolución es obligatorio y debe ser LIBERADA o MANTENIDA.");
        }

        Lote lote = loteRepo.findByIdWithLock(existente.getLote().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado con ID: " + existente.getLote().getId()));

        if (dto.getInspectorResponsableId() != null) {
            InspectorANMAT inspector = inspectorRepo.findById(dto.getInspectorResponsableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inspector ANMAT no encontrado con ID: " + dto.getInspectorResponsableId()));

            if (inspector.getEstado() != EstadoInspector.HABILITADO) {
                throw new IllegalStateException("El inspector ANMAT con ID " + inspector.getId()
                        + " no se encuentra en estado HABILITADO (estado actual: " + inspector.getEstado() + ").");
            }
            existente.setInspectorResponsable(inspector);
        }

        LocalDateTime fechaFin = dto.getFechaFin() != null
                ? dto.getFechaFin()
                : LocalDateTime.now(ZoneOffset.UTC);

        existente.setEstado(dto.getEstado());
        existente.setFechaFin(fechaFin);

        if (dto.getResolucionFinal() != null && !dto.getResolucionFinal().isBlank()) {
            existente.setResolucionFinal(dto.getResolucionFinal().trim());
        }

        if (dto.getObservaciones() != null && !dto.getObservaciones().isBlank()) {
            existente.setObservaciones(dto.getObservaciones().trim());
        }

        if (dto.getEstado() == EstadoCuarentena.LIBERADA) {
            lote.setEstado(EstadoLote.LIBERADO);
        } else if (dto.getEstado() == EstadoCuarentena.MANTENIDA) {
            lote.setEstado(EstadoLote.EN_CUARENTENA);
        }
        loteRepo.save(lote);

        return repo.save(existente);
    }

    @Transactional
    public Cuarentena update(Long id, CuarentenaRequestDTO dto) {
        Cuarentena existente = getById(id);

        if (dto.getEstado() != null && dto.getEstado() != existente.getEstado()) {
            throw new IllegalStateException(
                    "No está permitido modificar el estado de una cuarentena mediante PUT. Utilice el endpoint de resolución.");
        }

        if (dto.getLoteId() != null && !dto.getLoteId().equals(existente.getLote().getId())) {
            throw new IllegalStateException("No está permitido reasignar el lote de una cuarentena.");
        }

        if (dto.getCodigoCuarentena() != null) {
            String nuevoCodigo = dto.getCodigoCuarentena().trim();
            if (nuevoCodigo.isBlank()) {
                throw new IllegalArgumentException("El código de cuarentena no puede ser vacío.");
            }
            if (!nuevoCodigo.equals(existente.getCodigoCuarentena())) {
                if (repo.existsByCodigoCuarentenaAndIdNot(nuevoCodigo, id)) {
                    throw new DataIntegrityViolationException(
                            "Ya existe otra cuarentena registrada con el código: " + nuevoCodigo);
                }
                existente.setCodigoCuarentena(nuevoCodigo);
            }
        }

        if (dto.getMotivo() != null && !dto.getMotivo().isBlank()) {
            existente.setMotivo(dto.getMotivo().trim());
        }

        if (dto.getObservaciones() != null) {
            existente.setObservaciones(dto.getObservaciones().trim());
        }

        if (dto.getResolucionFinal() != null) {
            existente.setResolucionFinal(dto.getResolucionFinal().trim());
        }

        if (dto.getInspectorResponsableId() != null) {
            InspectorANMAT inspector = inspectorRepo.findById(dto.getInspectorResponsableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inspector ANMAT no encontrado con ID: " + dto.getInspectorResponsableId()));

            if (inspector.getEstado() != EstadoInspector.HABILITADO) {
                throw new IllegalStateException("El inspector ANMAT con ID " + inspector.getId()
                        + " no se encuentra en estado HABILITADO.");
            }
            existente.setInspectorResponsable(inspector);
        }

        return repo.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        // Verificar existencia primero
        getById(id);
        throw new IllegalStateException("No es posible eliminar la cuarentena con ID " + id
                + ". Los registros de cuarentena constituyen auditoría legal y trazabilidad de lotes y no pueden ser eliminados.");
    }
}
