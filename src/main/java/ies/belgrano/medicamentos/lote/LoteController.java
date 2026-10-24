package ies.belgrano.medicamentos.lote;

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
@RequestMapping("/api/lotes")
public class LoteController {

    private final LoteService service;
    private final LoteMapper mapper;

    public LoteController(LoteService service, LoteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<LoteResponseDTO>> getAll(
            @RequestParam(required = false) Long medicamentoId,
            @RequestParam(required = false) Long laboratorioId,
            @RequestParam(required = false) EstadoLote estado) {
        List<Lote> lotes = service.getAll(medicamentoId, laboratorioId, estado);
        List<LoteResponseDTO> dtoList = lotes.stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteResponseDTO> getById(@PathVariable Long id) {
        Lote lote = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(lote));
    }

    @PostMapping
    public ResponseEntity<LoteResponseDTO> create(@Valid @RequestBody LoteRequestDTO requestDTO) {
        Lote loteCreado = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(loteCreado));
    }

    @PostMapping("/{id}/unidades")
    public ResponseEntity<GeneracionUnidadesResponseDTO> generarUnidades(
            @PathVariable Long id,
            @Valid @RequestBody GenerarUnidadesRequestDTO requestDTO) {
        GeneracionUnidadesResponseDTO resumen = service.generarUnidades(id, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resumen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoteResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody LoteRequestDTO requestDTO) {
        Lote loteActualizado = service.update(id, requestDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(loteActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

