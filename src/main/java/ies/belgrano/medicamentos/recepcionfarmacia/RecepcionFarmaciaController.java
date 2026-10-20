package ies.belgrano.medicamentos.recepcionfarmacia;

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
@RequestMapping("/api/recepciones-farmacia")
public class RecepcionFarmaciaController {

    private final RecepcionFarmaciaService service;
    private final RecepcionFarmaciaMapper mapper;

    public RecepcionFarmaciaController(RecepcionFarmaciaService service, RecepcionFarmaciaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<RecepcionFarmaciaResponseDTO>> getAll(
            @RequestParam(required = false) Long despachoLogisticoId,
            @RequestParam(required = false) Long despachoId,
            @RequestParam(required = false) Long farmaciaId,
            @RequestParam(required = false) EstadoRecepcion estadoConformidad) {

        Long effectiveDespachoId = despachoLogisticoId != null ? despachoLogisticoId : despachoId;
        List<RecepcionFarmaciaResponseDTO> lista = service.getAll(effectiveDespachoId, farmaciaId, estadoConformidad).stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionFarmaciaResponseDTO> getById(@PathVariable Long id) {
        RecepcionFarmacia entity = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(entity));
    }

    @PostMapping
    public ResponseEntity<RecepcionFarmaciaResponseDTO> create(@Valid @RequestBody RecepcionFarmaciaRequestDTO dto) {
        RecepcionFarmacia nuevo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecepcionFarmaciaResponseDTO> update(@PathVariable Long id,
                                                               @Valid @RequestBody RecepcionFarmaciaRequestDTO dto) {
        RecepcionFarmacia actualizado = service.update(id, dto);
        return ResponseEntity.ok(mapper.toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
