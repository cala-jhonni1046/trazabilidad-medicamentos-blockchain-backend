package ies.belgrano.medicamentos.reporteciudadano;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes-ciudadanos")
public class ReporteCiudadanoController {

    @Autowired
    private ReporteCiudadanoService service;

    @Autowired
    private ReporteCiudadanoMapper mapper;

    @GetMapping
    public ResponseEntity<List<ReporteCiudadanoResponseDTO>> getAll() {
        List<ReporteCiudadanoResponseDTO> lista = service.getAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteCiudadanoResponseDTO> getById(@PathVariable Long id) {
        ReporteCiudadano reporte = service.getById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toResponseDTO(reporte));
    }

    @PostMapping
    public ResponseEntity<ReporteCiudadanoResponseDTO> create(@RequestBody ReporteCiudadanoRequestDTO dto) {
        try {
            ReporteCiudadano nuevo = service.create(mapper.toEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteCiudadanoResponseDTO> update(@PathVariable Long id, @RequestBody ReporteCiudadanoRequestDTO dto) {
        try {
            ReporteCiudadano actualizado = service.update(id, mapper.toEntity(dto));
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(mapper.toResponseDTO(actualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ReporteCiudadano reporte = service.getById(id);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
