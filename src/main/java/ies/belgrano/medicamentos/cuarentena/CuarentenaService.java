package ies.belgrano.medicamentos.cuarentena;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuarentenaService {

    @Autowired
    private CuarentenaRepository repo;

    public List<Cuarentena> getAll() {
        return repo.findAll();
    }

    public Cuarentena getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Cuarentena create(Cuarentena entidad) {
        return repo.save(entidad);
    }

    public Cuarentena update(Long id, Cuarentena entidad) {
        Cuarentena existente = this.getById(id);
        if (existente == null) {
            return null;
        } else {
            entidad.setId(id);
            return repo.save(entidad);
        }
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
