package ies.belgrano.medicamentos.operaciones.dispensacion;

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
@RequestMapping("/api/dispensaciones")
public class DispensacionController {

    @Autowired
    private DispensacionService service;

    @Autowired
    private DispensacionMapper mapper;

    @GetMapping
    public ResponseEntity<List<DispensacionResponseDTO>> getAll() {
        List<DispensacionResponseDTO> lista = service.getAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispensacionResponseDTO> getById(@PathVariable Long id) {
        Dispensacion dispensacion = service.getById(id);
        if (dispensacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toResponseDTO(dispensacion));
    }

    @PostMapping
    public ResponseEntity<DispensacionResponseDTO> create(@RequestBody DispensacionRequestDTO dto) {
        try {
            Dispensacion nuevo = service.create(mapper.toEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DispensacionResponseDTO> update(@PathVariable Long id, @RequestBody DispensacionRequestDTO dto) {
        try {
            Dispensacion actualizado = service.update(id, mapper.toEntity(dto));
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
        Dispensacion dispensacion = service.getById(id);
        if (dispensacion == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
