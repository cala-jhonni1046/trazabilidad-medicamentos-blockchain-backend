package ies.belgrano.medicamentos.reporteciudadano;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteCiudadanoService {

    @Autowired
    private ReporteCiudadanoRepository repo;

    public List<ReporteCiudadano> getAll() {
        return repo.findAll();
    }

    public ReporteCiudadano getById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public ReporteCiudadano create(ReporteCiudadano entidad) {
        return repo.save(entidad);
    }

    public ReporteCiudadano update(UUID id, ReporteCiudadano entidad) {
        ReporteCiudadano existente = this.getById(id);
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
