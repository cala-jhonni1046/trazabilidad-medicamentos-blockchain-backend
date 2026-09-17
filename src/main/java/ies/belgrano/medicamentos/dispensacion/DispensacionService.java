package ies.belgrano.medicamentos.dispensacion;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DispensacionService {

    @Autowired
    private DispensacionRepository repo;

    public List<Dispensacion> getAll() {
        return repo.findAll();
    }

    public Dispensacion getById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Dispensacion create(Dispensacion entidad) {
        return repo.save(entidad);
    }

    public Dispensacion update(UUID id, Dispensacion entidad) {
        Dispensacion existente = this.getById(id);
        if (existente == null) {
            return null;
        } else {
            entidad.setId(id);
            return repo.save(entidad);
        }
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
