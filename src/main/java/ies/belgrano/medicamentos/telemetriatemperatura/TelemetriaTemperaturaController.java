package ies.belgrano.medicamentos.telemetriatemperatura;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/telemetrias-temperatura")
public class TelemetriaTemperaturaController {

    private final TelemetriaTemperaturaService service;
    private final TelemetriaTemperaturaMapper mapper;

    public TelemetriaTemperaturaController(TelemetriaTemperaturaService service,
                                          TelemetriaTemperaturaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<TelemetriaTemperaturaResponseDTO>> getAll(
            @RequestParam(name = "despachoLogisticoId", required = false) Long despachoLogisticoId,
            @RequestParam(name = "despachoId", required = false) Long despachoId) {
        Long targetDespachoId = despachoLogisticoId != null ? despachoLogisticoId : despachoId;
        List<TelemetriaTemperaturaResponseDTO> lista = service.getAll(targetDespachoId).stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelemetriaTemperaturaResponseDTO> getById(@PathVariable Long id) {
        TelemetriaTemperatura entity = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(entity));
    }

    @PostMapping
    public ResponseEntity<TelemetriaTemperaturaResponseDTO> create(@Valid @RequestBody TelemetriaTemperaturaRequestDTO dto) {
        TelemetriaTemperatura nuevo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
    }
}

