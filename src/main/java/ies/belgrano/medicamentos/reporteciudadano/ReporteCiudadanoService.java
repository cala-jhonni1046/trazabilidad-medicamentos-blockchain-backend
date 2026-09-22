package ies.belgrano.medicamentos.reporteciudadano;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteCiudadanoService {

    @Autowired
    private ReporteCiudadanoRepository repo;

    public List<ReporteCiudadano> getAll() {
        return repo.findAll();
    }

    public ReporteCiudadano getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public ReporteCiudadano create(ReporteCiudadano reporte) {
        return repo.save(reporte);
    }

    public ReporteCiudadano update(Long id, ReporteCiudadano reporte) {
        ReporteCiudadano existente = this.getById(id);
        if (existente == null) {
            return null;
        } else {
            reporte.setId(id);
            return repo.save(reporte);
        }
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
