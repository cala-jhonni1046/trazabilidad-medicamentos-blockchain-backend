package ies.belgrano.medicamentos.despachologistico;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DespachoLogisticoService {

    private final DespachoLogisticoRepository repository;

    public DespachoLogisticoService(DespachoLogisticoRepository repository) {
        this.repository = repository;
    }

    public List<DespachoLogistico> getAll() {
        return repository.findAll();
    }

    public Optional<DespachoLogistico> getById(Long id) {
        return repository.findById(id);
    }

    public DespachoLogistico create(DespachoLogistico entidad) {
        entidad.setId(null);
        return repository.save(entidad);
    }

    public DespachoLogistico update(Long id, DespachoLogistico entidad) {
        return repository.findById(id).map(existente -> {
            existente.setNumeroRemito(entidad.getNumeroRemito());
            existente.setCodigoSeguimiento(entidad.getCodigoSeguimiento());
            existente.setOrigen(entidad.getOrigen());
            existente.setDestino(entidad.getDestino());
            existente.setTransportista(entidad.getTransportista());
            existente.setVehiculoPatente(entidad.getVehiculoPatente());
            existente.setFechaSalida(entidad.getFechaSalida());
            existente.setFechaEstimadaEntrega(entidad.getFechaEstimadaEntrega());
            existente.setFechaEntregaReal(entidad.getFechaEntregaReal());
            existente.setEstado(entidad.getEstado());
            existente.setTemperaturaMinimaPermitida(entidad.getTemperaturaMinimaPermitida());
            existente.setTemperaturaMaximaPermitida(entidad.getTemperaturaMaximaPermitida());
            existente.setObservaciones(entidad.getObservaciones());
            existente.setLote(entidad.getLote());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("DespachoLogistico no encontrado con ID: " + id));
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
