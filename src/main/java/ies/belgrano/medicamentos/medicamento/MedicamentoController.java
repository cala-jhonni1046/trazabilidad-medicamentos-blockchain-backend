package ies.belgrano.medicamentos.medicamento;

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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoService service;
    private final MedicamentoMapper mapper;

    public MedicamentoController(MedicamentoService service, MedicamentoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> getAll() {
        List<MedicamentoResponseDTO> dtoList = service.getAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> getById(@PathVariable Long id) {
        Medicamento medicamento = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(medicamento));
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> create(@Valid @RequestBody MedicamentoRequestDTO requestDTO) {
        Medicamento medicamentoCreado = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(medicamentoCreado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> update(@PathVariable Long id,
                                                         @Valid @RequestBody MedicamentoRequestDTO requestDTO) {
        Medicamento medicamentoActualizado = service.update(id, requestDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(medicamentoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}