package ies.belgrano.medicamentos.telemetriagps;

import java.util.List;

import ies.belgrano.medicamentos.security.AccesoOrganizacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.despachologistico.DespachoLogisticoRepository;
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;

@Service
public class TelemetriaGPSService {

    private final TelemetriaGPSRepository repo;
    private final DespachoLogisticoRepository despachoRepo;
    private final AccesoOrganizacion acceso;

    public TelemetriaGPSService(TelemetriaGPSRepository repo,
                                DespachoLogisticoRepository despachoRepo,
                                AccesoOrganizacion acceso) {
        this.repo = repo;
        this.despachoRepo = despachoRepo;
        this.acceso = acceso;
    }

    @Transactional(readOnly = true)
    public List<TelemetriaGPS> getAll(Long despachoLogisticoId) {
        boolean admin = acceso.esAdminOGlobal();
        Long orgId = acceso.organizacionActualId();
        if (!admin && orgId == null) {
            return List.of();
        }
        if (despachoLogisticoId != null) {
            boolean existe = admin ? despachoRepo.existsById(despachoLogisticoId)
                    : despachoRepo.esVisiblePorOrganizacion(despachoLogisticoId, orgId);
            if (!existe) {
                throw new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + despachoLogisticoId);
            }
            return repo.findByDespachoLogisticoIdOrderByFechaHoraAscIdAsc(despachoLogisticoId);
        }
        return admin ? repo.findAllByOrderByFechaHoraAscIdAsc() : repo.findVisiblesPorOrganizacion(orgId);
    }

    @Transactional(readOnly = true)
    public List<TelemetriaGPS> getAll() {
        return getAll(null);
    }

    @Transactional(readOnly = true)
    public TelemetriaGPS getById(Long id) {
        TelemetriaGPS lectura = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lectura de telemetría GPS no encontrada con ID: " + id));
        if (!acceso.esAdminOGlobal()) {
            Long orgId = acceso.organizacionActualId();
            if (orgId == null || !despachoRepo.esVisiblePorOrganizacion(lectura.getDespachoLogistico().getId(), orgId)) {
                throw new ResourceNotFoundException("Lectura de telemetría GPS no encontrada con ID: " + id);
            }
        }
        return lectura;
    }

    @Transactional
    public TelemetriaGPS create(TelemetriaGPSRequestDTO dto) {
        if (dto.getDespachoLogisticoId() == null) {
            throw new IllegalArgumentException("El ID de despacho logístico es obligatorio.");
        }
        if (!acceso.esAdminOGlobal()) {
            // Solo el laboratorio del lote o el distribuidor cargan telemetría de un despacho
            Long orgId = acceso.organizacionActualId();
            if (orgId == null || !despachoRepo.esGestionablePorOrganizacion(dto.getDespachoLogisticoId(), orgId)) {
                throw new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + dto.getDespachoLogisticoId());
            }
        }
        DespachoLogistico despacho = despachoRepo.findById(dto.getDespachoLogisticoId())
                .orElseThrow(() -> new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + dto.getDespachoLogisticoId()));

        if (dto.getLatitud() != null && (!Double.isFinite(dto.getLatitud()) || dto.getLatitud() < -90.0 || dto.getLatitud() > 90.0)) {
            throw new IllegalArgumentException("La latitud debe ser un número finito entre -90 y 90 grados.");
        }
        if (dto.getLongitud() != null && (!Double.isFinite(dto.getLongitud()) || dto.getLongitud() < -180.0 || dto.getLongitud() > 180.0)) {
            throw new IllegalArgumentException("La longitud debe ser un número finito entre -180 y 180 grados.");
        }
        if (dto.getAltitud() != null && !Double.isFinite(dto.getAltitud())) {
            throw new IllegalArgumentException("La altitud debe ser un valor numérico finito.");
        }
        if (dto.getVelocidad() != null && !Double.isFinite(dto.getVelocidad())) {
            throw new IllegalArgumentException("La velocidad debe ser un valor numérico finito.");
        }

        TelemetriaGPS entidad = new TelemetriaGPS();
        entidad.setDispositivoGpsId(dto.getDispositivoGpsId() != null ? dto.getDispositivoGpsId().trim() : null);
        entidad.setLatitud(dto.getLatitud());
        entidad.setLongitud(dto.getLongitud());
        entidad.setAltitud(dto.getAltitud());
        entidad.setVelocidad(dto.getVelocidad());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setDespachoLogistico(despacho);
        entidad.setDireccionAproximada(dto.getDireccionAproximada() != null ? dto.getDireccionAproximada().trim() : null);

        return repo.save(entidad);
    }
}

