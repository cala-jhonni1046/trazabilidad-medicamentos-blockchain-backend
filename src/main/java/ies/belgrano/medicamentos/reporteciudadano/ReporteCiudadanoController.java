package ies.belgrano.medicamentos.reporteciudadano;

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
@RequestMapping("/api/reportes-ciudadanos")
public class ReporteCiudadanoController {

    private final ReporteCiudadanoService service;
    private final ReporteCiudadanoMapper mapper;

    public ReporteCiudadanoController(ReporteCiudadanoService service, ReporteCiudadanoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ReporteCiudadanoResponseDTO>> getAll(
            @RequestParam(required = false) EstadoReporte estado,
            @RequestParam(required = false) Long unidadTrazableId,
            @RequestParam(required = false) Long inspectorId) {
        List<ReporteCiudadanoResponseDTO> lista = service.getAll(estado, unidadTrazableId, inspectorId).stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteCiudadanoResponseDTO> getById(@PathVariable Long id) {
        ReporteCiudadano reporte = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(reporte));
    }

    @PostMapping
    public ResponseEntity<ReporteCiudadanoResponseDTO> create(@Valid @RequestBody ReporteCiudadanoRequestDTO dto) {
        ReporteCiudadano nuevo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
    }

    @PostMapping(path = { "/{id}/iniciar-revision" })
    public ResponseEntity<ReporteCiudadanoResponseDTO> iniciarRevision(
            @PathVariable Long id,
            @RequestBody(required = false) ReporteRevisionRequestDTO dto) {
        ReporteCiudadano enRevision = service.iniciarRevision(id, dto);
        return ResponseEntity.ok(mapper.toResponseDTO(enRevision));
    }

    @PutMapping(path = { "/{id}/iniciar-revision" })
    public ResponseEntity<ReporteCiudadanoResponseDTO> iniciarRevisionPut(
            @PathVariable Long id,
            @RequestBody(required = false) ReporteRevisionRequestDTO dto) {
        ReporteCiudadano enRevision = service.iniciarRevision(id, dto);
        return ResponseEntity.ok(mapper.toResponseDTO(enRevision));
    }

    @PostMapping(path = { "/{id}/resolver" })
    public ResponseEntity<ReporteCiudadanoResponseDTO> resolver(
            @PathVariable Long id,
            @RequestBody(required = false) ReporteResolucionRequestDTO dto) {
        ReporteCiudadano resuelto = service.resolver(id, dto);
        return ResponseEntity.ok(mapper.toResponseDTO(resuelto));
    }

    @PutMapping(path = { "/{id}/resolver" })
    public ResponseEntity<ReporteCiudadanoResponseDTO> resolverPut(
            @PathVariable Long id,
            @RequestBody(required = false) ReporteResolucionRequestDTO dto) {
        ReporteCiudadano resuelto = service.resolver(id, dto);
        return ResponseEntity.ok(mapper.toResponseDTO(resuelto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteCiudadanoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ReporteCiudadanoRequestDTO dto) {
        ReporteCiudadano actualizado = service.update(id, dto);
        return ResponseEntity.ok(mapper.toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
