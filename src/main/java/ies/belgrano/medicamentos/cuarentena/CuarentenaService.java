package ies.belgrano.medicamentos.cuarentena;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CuarentenaService {

    private final CuarentenaRepository repository;

    public CuarentenaService(CuarentenaRepository repository) {
        this.repository = repository;
    }

    public List<Cuarentena> getAll() {
        return repository.findAll();
    }

    public Optional<Cuarentena> getById(Long id) {
        return repository.findById(id);
    }

    public Cuarentena create(Cuarentena entidad) {
        entidad.setId(null);
        return repository.save(entidad);
    }

    public Cuarentena update(Long id, Cuarentena entidad) {
        return repository.findById(id).map(existente -> {
            existente.setCodigoCuarentena(entidad.getCodigoCuarentena());
            existente.setFechaInicio(entidad.getFechaInicio());
            existente.setFechaFin(entidad.getFechaFin());
            existente.setMotivo(entidad.getMotivo());
            existente.setEstado(entidad.getEstado());
            existente.setResponsable(entidad.getResponsable());
            existente.setUbicacionFisica(entidad.getUbicacionFisica());
            existente.setLoteId(entidad.getLoteId());
            existente.setUnidadTrazableId(entidad.getUnidadTrazableId());
            existente.setResolucionFinal(entidad.getResolucionFinal());
            existente.setObservaciones(entidad.getObservaciones());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("Cuarentena no encontrada con ID: " + id));
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
