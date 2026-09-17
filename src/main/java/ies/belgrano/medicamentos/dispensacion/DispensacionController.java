package ies.belgrano.medicamentos.dispensacion;

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
import java.util.UUID;

@RestController
@RequestMapping("/api/dispensaciones")
public class DispensacionController {

    @Autowired
    private DispensacionService service;

    @PostMapping
    public ResponseEntity<Dispensacion> create(@RequestBody Dispensacion entidad) {
        try {
            Dispensacion nuevo = service.create(entidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Dispensacion>> getAll() {
        List<Dispensacion> lista = service.getAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dispensacion> getById(@PathVariable UUID id) {
        Dispensacion entidad = service.getById(id);
        if (entidad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dispensacion> update(@PathVariable UUID id, @RequestBody Dispensacion entidad) {
        try {
            Dispensacion actualizado = service.update(id, entidad);
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Dispensacion entidad = service.getById(id);
        if (entidad == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
