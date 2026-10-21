package ies.belgrano.medicamentos.unidadTrazable;

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
@RequestMapping("/api/unidades-trazables")
public class UnidadTrazableController {

    private final UnidadTrazableService service;
    private final UnidadTrazableMapper mapper;

    public UnidadTrazableController(UnidadTrazableService service, UnidadTrazableMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UnidadTrazableResponseDTO>> getAll(
            @RequestParam(required = false) Long loteId,
            @RequestParam(required = false) String codigoQr) {
        List<UnidadTrazableResponseDTO> lista = service.getAll(loteId, codigoQr).stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadTrazableResponseDTO> getById(@PathVariable Long id) {
        UnidadTrazable entity = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(entity));
    }

    @GetMapping("/qr/{codigoQr}")
    public ResponseEntity<UnidadTrazableResponseDTO> getByCodigoQR(@PathVariable String codigoQr) {
        UnidadTrazable entity = service.getByCodigoQR(codigoQr);
        return ResponseEntity.ok(mapper.toResponseDTO(entity));
    }

    @PostMapping
    public ResponseEntity<UnidadTrazableResponseDTO> create(@Valid @RequestBody UnidadTrazableRequestDTO requestDTO) {
        UnidadTrazable nueva = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nueva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadTrazableResponseDTO> update(@PathVariable Long id,
                                                           @Valid @RequestBody UnidadTrazableRequestDTO requestDTO) {
        UnidadTrazable actualizada = service.update(id, requestDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}