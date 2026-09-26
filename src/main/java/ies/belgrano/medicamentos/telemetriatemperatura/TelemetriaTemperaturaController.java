package ies.belgrano.medicamentos.telemetriatemperatura;

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
@RequestMapping("/api/telemetrias-temperatura")
public class TelemetriaTemperaturaController {

    private final TelemetriaTemperaturaService service;

    public TelemetriaTemperaturaController(TelemetriaTemperaturaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TelemetriaTemperatura>> getAll() {
        List<TelemetriaTemperatura> lista = service.getAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelemetriaTemperatura> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TelemetriaTemperatura> create(@RequestBody TelemetriaTemperatura entidad) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entidad));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelemetriaTemperatura> update(@PathVariable Long id, @RequestBody TelemetriaTemperatura entidad) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            return ResponseEntity.ok(service.update(id, entidad));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
