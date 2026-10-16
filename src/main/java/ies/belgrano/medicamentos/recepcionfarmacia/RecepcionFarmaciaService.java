package ies.belgrano.medicamentos.recepcionfarmacia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecepcionFarmaciaService {

    @Autowired
    private RecepcionFarmaciaRepository repo;

    public List<RecepcionFarmacia> getAll() {
        return repo.findAll();
    }

    public RecepcionFarmacia getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public RecepcionFarmacia create(RecepcionFarmacia entidad) {
        return repo.save(entidad);
    }

    public RecepcionFarmacia update(Long id, RecepcionFarmacia entidad) {
        RecepcionFarmacia existente = this.getById(id);
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
