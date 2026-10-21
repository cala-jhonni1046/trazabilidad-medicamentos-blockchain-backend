package ies.belgrano.medicamentos.dispensacion;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dispensaciones")
public class DispensacionController {

    private final DispensacionService service;
    private final DispensacionMapper mapper;

    public DispensacionController(DispensacionService service, DispensacionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DispensacionResponseDTO>> getAll(
            @RequestParam(required = false) Long farmaciaId,
            @RequestParam(required = false) Long unidadTrazableId) {
        List<DispensacionResponseDTO> lista = service.getAll(farmaciaId, unidadTrazableId).stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispensacionResponseDTO> getById(@PathVariable Long id) {
        Dispensacion dispensacion = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(dispensacion));
    }

    @GetMapping("/unidad/{unidadId}")
    public ResponseEntity<DispensacionResponseDTO> getByUnidadTrazableId(@PathVariable Long unidadId) {
        Dispensacion dispensacion = service.getByUnidadTrazableId(unidadId);
        return ResponseEntity.ok(mapper.toResponseDTO(dispensacion));
    }

    @PostMapping
    public ResponseEntity<DispensacionResponseDTO> create(@Valid @RequestBody DispensacionRequestDTO dto) {
        Dispensacion nueva = service.dispensar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nueva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DispensacionResponseDTO> update(@PathVariable Long id,
                                                          @RequestBody(required = false) DispensacionRequestDTO dto) {
        Dispensacion actualizado = service.update(id, dto);
        return ResponseEntity.ok(mapper.toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
