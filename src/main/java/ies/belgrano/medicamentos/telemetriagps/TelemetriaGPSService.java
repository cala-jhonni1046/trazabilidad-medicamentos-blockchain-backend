package ies.belgrano.medicamentos.telemetriagps;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.despachologistico.DespachoLogisticoRepository;
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;

@Service
public class TelemetriaGPSService {

    private final TelemetriaGPSRepository repo;
    private final DespachoLogisticoRepository despachoRepo;

    public TelemetriaGPSService(TelemetriaGPSRepository repo,
                                DespachoLogisticoRepository despachoRepo) {
        this.repo = repo;
        this.despachoRepo = despachoRepo;
    }

    @Transactional(readOnly = true)
    public List<TelemetriaGPS> getAll(Long despachoLogisticoId) {
        if (despachoLogisticoId != null) {
            if (!despachoRepo.existsById(despachoLogisticoId)) {
                throw new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + despachoLogisticoId);
            }
            return repo.findByDespachoLogisticoIdOrderByFechaHoraAscIdAsc(despachoLogisticoId);
        }
        return repo.findAllByOrderByFechaHoraAscIdAsc();
    }

    @Transactional(readOnly = true)
    public List<TelemetriaGPS> getAll() {
        return getAll(null);
    }

    @Transactional(readOnly = true)
    public TelemetriaGPS getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lectura de telemetría GPS no encontrada con ID: " + id));
    }

    @Transactional
    public TelemetriaGPS create(TelemetriaGPSRequestDTO dto) {
        if (dto.getDespachoLogisticoId() == null) {
            throw new IllegalArgumentException("El ID de despacho logístico es obligatorio.");
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

