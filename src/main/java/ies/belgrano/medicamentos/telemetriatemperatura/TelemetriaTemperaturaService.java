package ies.belgrano.medicamentos.telemetriatemperatura;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TelemetriaTemperaturaService {

    private final TelemetriaTemperaturaRepository repository;

    public TelemetriaTemperaturaService(TelemetriaTemperaturaRepository repository) {
        this.repository = repository;
    }

    public List<TelemetriaTemperatura> getAll() {
        return repository.findAll();
    }

    public Optional<TelemetriaTemperatura> getById(Long id) {
        return repository.findById(id);
    }

    public TelemetriaTemperatura create(TelemetriaTemperatura entidad) {
        entidad.setId(null);
        return repository.save(entidad);
    }

    public TelemetriaTemperatura update(Long id, TelemetriaTemperatura entidad) {
        return repository.findById(id).map(existente -> {
            existente.setSensorId(entidad.getSensorId());
            existente.setTemperatura(entidad.getTemperatura());
            existente.setFechaHora(entidad.getFechaHora());
            existente.setAlertaExcursion(entidad.getAlertaExcursion());
            existente.setDespachoLogisticoId(entidad.getDespachoLogisticoId());
            existente.setObservaciones(entidad.getObservaciones());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("TelemetriaTemperatura no encontrada con ID: " + id));
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
