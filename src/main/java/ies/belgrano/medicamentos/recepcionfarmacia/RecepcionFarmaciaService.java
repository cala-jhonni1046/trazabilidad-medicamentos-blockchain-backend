package ies.belgrano.medicamentos.recepcionfarmacia;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecepcionFarmaciaService {

    private final RecepcionFarmaciaRepository repository;

    public RecepcionFarmaciaService(RecepcionFarmaciaRepository repository) {
        this.repository = repository;
    }

    public List<RecepcionFarmacia> getAll() {
        return repository.findAll();
    }

    public Optional<RecepcionFarmacia> getById(Long id) {
        return repository.findById(id);
    }

    public RecepcionFarmacia create(RecepcionFarmacia entidad) {
        entidad.setId(null);
        return repository.save(entidad);
    }

    public RecepcionFarmacia update(Long id, RecepcionFarmacia entidad) {
        return repository.findById(id).map(existente -> {
            existente.setNumeroComprobante(entidad.getNumeroComprobante());
            existente.setFechaHoraRecepcion(entidad.getFechaHoraRecepcion());
            existente.setFarmaciaCuit(entidad.getFarmaciaCuit());
            existente.setFarmaciaNombre(entidad.getFarmaciaNombre());
            existente.setFarmaceuticoResponsable(entidad.getFarmaceuticoResponsable());
            existente.setDespachoLogisticoId(entidad.getDespachoLogisticoId());
            existente.setEstadoConformidad(entidad.getEstadoConformidad());
            existente.setCadenaFrioIntacta(entidad.getCadenaFrioIntacta());
            existente.setObservaciones(entidad.getObservaciones());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("RecepcionFarmacia no encontrada con ID: " + id));
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
