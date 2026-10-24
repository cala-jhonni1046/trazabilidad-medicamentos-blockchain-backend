package ies.belgrano.medicamentos.inspector;

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
@RequestMapping("/api/inspectores")
public class InspectorANMATController {

    private final InspectorANMATService service;
    private final InspectorANMATMapper mapper;

    public InspectorANMATController(InspectorANMATService service, InspectorANMATMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<InspectorANMATResponseDTO>> getAll(@RequestParam(required = false) String dni) {
        List<InspectorANMATResponseDTO> dtoList = service.getAll(dni).stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspectorANMATResponseDTO> getById(@PathVariable Long id) {
        InspectorANMAT inspector = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(inspector));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<InspectorANMATResponseDTO> getByUsuarioId(@PathVariable Long usuarioId) {
        InspectorANMAT inspector = service.getByUsuarioId(usuarioId);
        return ResponseEntity.ok(mapper.toResponseDTO(inspector));
    }

    @PostMapping
    public ResponseEntity<InspectorANMATResponseDTO> create(@Valid @RequestBody InspectorANMATRequestDTO requestDTO) {
        InspectorANMAT inspectorCreado = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(inspectorCreado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InspectorANMATResponseDTO> update(@PathVariable Long id,
                                                            @Valid @RequestBody InspectorANMATRequestDTO requestDTO) {
        InspectorANMAT inspectorActualizado = service.update(id, requestDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(inspectorActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
