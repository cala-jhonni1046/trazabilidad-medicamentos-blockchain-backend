package ies.belgrano.medicamentos.recepcionfarmacia;

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
@RequestMapping("/api/recepciones-farmacia")
public class RecepcionFarmaciaController {

    @Autowired
    private RecepcionFarmaciaService service;

    @Autowired
    private RecepcionFarmaciaMapper mapper;

    @GetMapping
    public ResponseEntity<List<RecepcionFarmaciaResponseDTO>> getAll() {
        List<RecepcionFarmaciaResponseDTO> lista = service.getAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionFarmaciaResponseDTO> getById(@PathVariable Long id) {
        RecepcionFarmacia entity = service.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toResponseDTO(entity));
    }

    @PostMapping
    public ResponseEntity<RecepcionFarmaciaResponseDTO> create(@RequestBody RecepcionFarmaciaRequestDTO dto) {
        try {
            RecepcionFarmacia nuevo = service.create(mapper.toEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecepcionFarmaciaResponseDTO> update(@PathVariable Long id, @RequestBody RecepcionFarmaciaRequestDTO dto) {
        try {
            RecepcionFarmacia actualizado = service.update(id, mapper.toEntity(dto));
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
        RecepcionFarmacia entity = service.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
