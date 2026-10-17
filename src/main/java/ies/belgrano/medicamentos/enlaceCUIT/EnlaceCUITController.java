package ies.belgrano.medicamentos.enlaceCUIT;

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
@RequestMapping("/api/enlaces-cuit")
public class EnlaceCUITController {

    private final EnlaceCUITService service;
    private final EnlaceCUITMapper mapper;

    public EnlaceCUITController(EnlaceCUITService service, EnlaceCUITMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<EnlaceCUITResponseDTO>> getAll(@RequestParam(required = false) EstadoEnlaceCuit estado) {
        List<EnlaceCUITResponseDTO> dtoList = service.getAll(estado).stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnlaceCUITResponseDTO> getById(@PathVariable Long id) {
        EnlaceCUIT enlaceCUIT = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(enlaceCUIT));
    }

    @PostMapping
    public ResponseEntity<EnlaceCUITResponseDTO> create(@Valid @RequestBody EnlaceCUITRequestDTO requestDTO) {
        EnlaceCUIT enlaceCUITCreado = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(enlaceCUITCreado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnlaceCUITResponseDTO> update(@PathVariable Long id,
                                                        @RequestBody EnlaceCUITRequestDTO requestDTO) {
        EnlaceCUIT enlaceCUITActualizado = service.update(id, requestDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(enlaceCUITActualizado));
    }

    @PostMapping("/{id}/aprobar")
    public ResponseEntity<EnlaceCUITResponseDTO> aprobar(@PathVariable Long id,
                                                         @Valid @RequestBody EnlaceResolucionRequestDTO requestDTO) {
        EnlaceCUIT enlaceAprobado = service.aprobar(id, requestDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(enlaceAprobado));
    }

    @PostMapping("/{id}/rechazar")
    public ResponseEntity<EnlaceCUITResponseDTO> rechazar(@PathVariable Long id,
                                                          @Valid @RequestBody EnlaceResolucionRequestDTO requestDTO) {
        EnlaceCUIT enlaceRechazado = service.rechazar(id, requestDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(enlaceRechazado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
