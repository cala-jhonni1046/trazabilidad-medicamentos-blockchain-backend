package ies.belgrano.medicamentos.operaciones.dispensacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DispensacionService {

    @Autowired
    private DispensacionRepository repo;

    public List<Dispensacion> getAll() {
        return repo.findAll();
    }

    public Dispensacion getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Dispensacion create(Dispensacion dispensacion) {
        return repo.save(dispensacion);
    }

    public Dispensacion update(Long id, Dispensacion dispensacion) {
        Dispensacion existente = this.getById(id);
        if (existente == null) {
            return null;
        } else {
            dispensacion.setId(id);
            return repo.save(dispensacion);
        }
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
