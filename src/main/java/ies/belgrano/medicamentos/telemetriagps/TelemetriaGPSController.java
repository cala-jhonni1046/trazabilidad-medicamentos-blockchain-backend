package ies.belgrano.medicamentos.telemetriagps;

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
@RequestMapping("/api/telemetrias-gps")
public class TelemetriaGPSController {

    private final TelemetriaGPSService service;
    private final TelemetriaGPSMapper mapper;

    public TelemetriaGPSController(TelemetriaGPSService service,
                                   TelemetriaGPSMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<TelemetriaGPSResponseDTO>> getAll(
            @RequestParam(name = "despachoLogisticoId", required = false) Long despachoLogisticoId,
            @RequestParam(name = "despachoId", required = false) Long despachoId) {
        Long targetDespachoId = despachoLogisticoId != null ? despachoLogisticoId : despachoId;
        List<TelemetriaGPSResponseDTO> lista = service.getAll(targetDespachoId).stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelemetriaGPSResponseDTO> getById(@PathVariable Long id) {
        TelemetriaGPS entity = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(entity));
    }

    @PostMapping
    public ResponseEntity<TelemetriaGPSResponseDTO> create(@Valid @RequestBody TelemetriaGPSRequestDTO dto) {
        TelemetriaGPS nuevo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
    }
}

