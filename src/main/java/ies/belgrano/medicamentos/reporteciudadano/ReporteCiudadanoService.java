package ies.belgrano.medicamentos.reporteciudadano;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.inspector.EstadoInspector;
import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.inspector.InspectorANMATRepository;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazableRepository;

@Service
public class ReporteCiudadanoService {

    private final ReporteCiudadanoRepository repo;
    private final UnidadTrazableRepository unidadTrazableRepo;
    private final InspectorANMATRepository inspectorRepo;

    public ReporteCiudadanoService(
            ReporteCiudadanoRepository repo,
            UnidadTrazableRepository unidadTrazableRepo,
            InspectorANMATRepository inspectorRepo) {
        this.repo = repo;
        this.unidadTrazableRepo = unidadTrazableRepo;
        this.inspectorRepo = inspectorRepo;
    }

    @Transactional(readOnly = true)
    public List<ReporteCiudadano> getAll(EstadoReporte estado, Long unidadTrazableId, Long inspectorId) {
        if (estado == null && unidadTrazableId == null && inspectorId == null) {
            return repo.findAll();
        }
        return repo.findByFiltros(estado, unidadTrazableId, inspectorId);
    }

    @Transactional(readOnly = true)
    public ReporteCiudadano getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte ciudadano no encontrado con ID: " + id));
    }

    @Transactional
    public ReporteCiudadano create(ReporteCiudadanoRequestDTO dto) {
        if (dto.getMotivoReporte() == null || dto.getMotivoReporte().trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo del reporte es obligatorio.");
        }

        UnidadTrazable unidad = null;
        if (dto.getCodigoUnicoQR() != null && !dto.getCodigoUnicoQR().trim().isEmpty()) {
            String codigoQr = dto.getCodigoUnicoQR().trim();
            unidad = unidadTrazableRepo.findByCodigoUnicoQR(codigoQr)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró ninguna unidad trazable para el código QR: " + codigoQr));

            if (dto.getUnidadTrazableId() != null && !unidad.getId().equals(dto.getUnidadTrazableId())) {
                throw new IllegalArgumentException("El código QR y el ID de unidad trazable especificados no coinciden.");
            }
        } else if (dto.getUnidadTrazableId() != null) {
            unidad = unidadTrazableRepo.findById(dto.getUnidadTrazableId())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró la unidad trazable con ID: " + dto.getUnidadTrazableId()));
        }

        ReporteCiudadano reporte = new ReporteCiudadano();
        reporte.setMotivoReporte(dto.getMotivoReporte().trim());
        reporte.setFechaHoraReporte(dto.getFechaHoraReporte() != null ? dto.getFechaHoraReporte() : LocalDateTime.now());
        // Regla: un reporte nuevo siempre inicia en PENDIENTE; el cliente no puede crearlo resuelto ni asignar inspector
        reporte.setEstado(EstadoReporte.PENDIENTE);
        reporte.setUnidadTrazable(unidad);
        reporte.setInspector(null);

        return repo.save(reporte);
    }

    @Transactional
    public ReporteCiudadano iniciarRevision(Long id, ReporteRevisionRequestDTO dto) {
        ReporteCiudadano reporte = getById(id);

        if (reporte.getEstado() != EstadoReporte.PENDIENTE) {
            throw new IllegalStateException("Solo se puede iniciar la revisión de un reporte que esté en estado PENDIENTE.");
        }

        if (dto != null && dto.getInspectorAsignadoId() != null) {
            InspectorANMAT inspector = inspectorRepo.findById(dto.getInspectorAsignadoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inspector ANMAT no encontrado con ID: " + dto.getInspectorAsignadoId()));

            if (inspector.getEstado() != EstadoInspector.HABILITADO) {
                throw new IllegalStateException("El inspector asignado no se encuentra habilitado.");
            }
            reporte.setInspector(inspector);
        }

        reporte.setEstado(EstadoReporte.EN_REVISION);
        return repo.save(reporte);
    }

    @Transactional
    public ReporteCiudadano resolver(Long id, ReporteResolucionRequestDTO dto) {
        ReporteCiudadano reporte = getById(id);

        if (reporte.getEstado() != EstadoReporte.EN_REVISION) {
            throw new IllegalStateException("Solo se puede resolver un reporte que esté en estado EN_REVISION.");
        }

        if (dto != null && dto.getInspectorAsignadoId() != null) {
            InspectorANMAT inspector = inspectorRepo.findById(dto.getInspectorAsignadoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inspector ANMAT no encontrado con ID: " + dto.getInspectorAsignadoId()));

            if (inspector.getEstado() != EstadoInspector.HABILITADO) {
                throw new IllegalStateException("El inspector asignado no se encuentra habilitado.");
            }
            reporte.setInspector(inspector);
        }

        reporte.setEstado(EstadoReporte.RESUELTO);
        return repo.save(reporte);
    }

    @Transactional
    public ReporteCiudadano update(Long id, ReporteCiudadanoRequestDTO dto) {
        ReporteCiudadano reporte = getById(id);

        if (reporte.getEstado() == EstadoReporte.RESUELTO) {
            throw new IllegalStateException("No se permite modificar un reporte en estado RESUELTO.");
        }

        if (dto.getEstado() != null && dto.getEstado() != reporte.getEstado()) {
            throw new IllegalStateException("No se permite cambiar el estado del reporte mediante este endpoint. Utilice las operaciones de transición explícitas.");
        }

        if (dto.getMotivoReporte() != null && !dto.getMotivoReporte().trim().isEmpty()) {
            reporte.setMotivoReporte(dto.getMotivoReporte().trim());
        }

        if (dto.getCodigoUnicoQR() != null && !dto.getCodigoUnicoQR().trim().isEmpty()) {
            String codigoQr = dto.getCodigoUnicoQR().trim();
            UnidadTrazable unidad = unidadTrazableRepo.findByCodigoUnicoQR(codigoQr)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró ninguna unidad trazable para el código QR: " + codigoQr));
            reporte.setUnidadTrazable(unidad);
        } else if (dto.getUnidadTrazableId() != null) {
            UnidadTrazable unidad = unidadTrazableRepo.findById(dto.getUnidadTrazableId())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró la unidad trazable con ID: " + dto.getUnidadTrazableId()));
            reporte.setUnidadTrazable(unidad);
        }

        return repo.save(reporte);
    }

    @Transactional
    public void delete(Long id) {
        // Verifica existencia primero para lanzar 404 si no existe
        getById(id);
        throw new IllegalStateException("No se permite eliminar reportes ciudadanos para preservar el registro y trazabilidad.");
    }
}
