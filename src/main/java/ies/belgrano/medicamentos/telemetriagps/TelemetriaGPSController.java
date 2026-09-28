package ies.belgrano.medicamentos.telemetriagps;

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
@RequestMapping("/api/telemetrias-gps")
public class TelemetriaGPSController {

    @Autowired
    private TelemetriaGPSService service;

    @Autowired
    private TelemetriaGPSMapper mapper;

    @GetMapping
    public ResponseEntity<List<TelemetriaGPSResponseDTO>> getAll() {
        List<TelemetriaGPSResponseDTO> lista = service.getAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelemetriaGPSResponseDTO> getById(@PathVariable Long id) {
        TelemetriaGPS entity = service.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toResponseDTO(entity));
    }

    @PostMapping
    public ResponseEntity<TelemetriaGPSResponseDTO> create(@RequestBody TelemetriaGPSRequestDTO dto) {
        try {
            TelemetriaGPS nuevo = service.create(mapper.toEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelemetriaGPSResponseDTO> update(@PathVariable Long id, @RequestBody TelemetriaGPSRequestDTO dto) {
        try {
            TelemetriaGPS actualizado = service.update(id, mapper.toEntity(dto));
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
        TelemetriaGPS entity = service.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
