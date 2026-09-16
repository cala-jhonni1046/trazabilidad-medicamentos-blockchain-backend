package ies.belgrano.medicamentos.telemetriagps;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TelemetriaGPSService {

    private final TelemetriaGPSRepository repository;

    public TelemetriaGPSService(TelemetriaGPSRepository repository) {
        this.repository = repository;
    }

    public List<TelemetriaGPS> getAll() {
        return repository.findAll();
    }

    public Optional<TelemetriaGPS> getById(Long id) {
        return repository.findById(id);
    }

    public TelemetriaGPS create(TelemetriaGPS entidad) {
        entidad.setId(null);
        return repository.save(entidad);
    }

    public TelemetriaGPS update(Long id, TelemetriaGPS entidad) {
        return repository.findById(id).map(existente -> {
            existente.setDispositivoGpsId(entidad.getDispositivoGpsId());
            existente.setLatitud(entidad.getLatitud());
            existente.setLongitud(entidad.getLongitud());
            existente.setAltitud(entidad.getAltitud());
            existente.setVelocidad(entidad.getVelocidad());
            existente.setFechaHora(entidad.getFechaHora());
            existente.setDespachoLogisticoId(entidad.getDespachoLogisticoId());
            existente.setDireccionAproximada(entidad.getDireccionAproximada());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("TelemetriaGPS no encontrada con ID: " + id));
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
