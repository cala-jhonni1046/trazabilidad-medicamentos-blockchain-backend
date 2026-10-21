package ies.belgrano.medicamentos.despachologistico;

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
@RequestMapping("/api/despachos-logisticos")
public class DespachoLogisticoController {

    private final DespachoLogisticoService service;
    private final DespachoLogisticoMapper despachoMapper;
    private final EventoCustodiaMapper eventoMapper;

    public DespachoLogisticoController(DespachoLogisticoService service,
                                      DespachoLogisticoMapper despachoMapper,
                                      EventoCustodiaMapper eventoMapper) {
        this.service = service;
        this.despachoMapper = despachoMapper;
        this.eventoMapper = eventoMapper;
    }

    @GetMapping
    public ResponseEntity<List<DespachoLogisticoResponseDTO>> getAll(
            @RequestParam(required = false) Long loteId,
            @RequestParam(required = false) Long distribuidorId,
            @RequestParam(required = false) EstadoDespacho estado) {
        List<DespachoLogisticoResponseDTO> lista = service.getAll(loteId, distribuidorId, estado).stream()
                .map(despachoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoLogisticoResponseDTO> getById(@PathVariable Long id) {
        DespachoLogistico entity = service.getById(id);
        return ResponseEntity.ok(despachoMapper.toResponseDTO(entity));
    }

    @PostMapping
    public ResponseEntity<DespachoLogisticoResponseDTO> create(@Valid @RequestBody DespachoLogisticoRequestDTO dto) {
        DespachoLogistico nuevo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(despachoMapper.toResponseDTO(nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespachoLogisticoResponseDTO> update(@PathVariable Long id,
                                                               @Valid @RequestBody DespachoLogisticoRequestDTO dto) {
        DespachoLogistico actualizado = service.update(id, dto);
        return ResponseEntity.ok(despachoMapper.toResponseDTO(actualizado));
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<DespachoLogisticoResponseDTO> iniciar(
            @PathVariable Long id,
            @Valid @RequestBody(required = false) TransicionDespachoRequestDTO dto) {
        DespachoLogistico enTransito = service.iniciarDespacho(id, dto);
        return ResponseEntity.ok(despachoMapper.toResponseDTO(enTransito));
    }

    @PostMapping(path = { "/{id}/entregar", "/{id}/recibir" })
    public ResponseEntity<Void> entregarSoloPorRecepcion(@PathVariable Long id) {
        service.getById(id);
        throw new IllegalStateException("La entrega se confirma exclusivamente mediante una recepción de farmacia.");
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<DespachoLogisticoResponseDTO> cancelar(
            @PathVariable Long id,
            @Valid @RequestBody(required = false) TransicionDespachoRequestDTO dto) {
        DespachoLogistico cancelado = service.cancelarDespacho(id, dto);
        return ResponseEntity.ok(despachoMapper.toResponseDTO(cancelado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = { "/{id}/eventos", "/{id}/eventos-custodia" })
    public ResponseEntity<List<EventoCustodiaResponseDTO>> getEventos(@PathVariable Long id) {
        List<EventoCustodiaResponseDTO> eventos = service.getEventosByDespachoId(id).stream()
                .map(eventoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(eventos);
    }

    @PostMapping(path = { "/{id}/eventos", "/{id}/eventos-custodia" })
    public ResponseEntity<EventoCustodiaResponseDTO> registrarEvento(
            @PathVariable Long id,
            @Valid @RequestBody EventoCustodiaRequestDTO dto) {
        EventoCustodia eventoCreado = service.registrarEvento(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoMapper.toResponseDTO(eventoCreado));
    }
}
