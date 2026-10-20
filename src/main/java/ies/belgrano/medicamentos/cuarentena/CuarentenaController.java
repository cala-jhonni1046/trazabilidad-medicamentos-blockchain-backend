package ies.belgrano.medicamentos.cuarentena;

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
@RequestMapping("/api/cuarentenas")
public class CuarentenaController {

    private final CuarentenaService service;
    private final CuarentenaMapper mapper;

    public CuarentenaController(CuarentenaService service, CuarentenaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CuarentenaResponseDTO>> getAll(
            @RequestParam(required = false) Long loteId,
            @RequestParam(required = false) EstadoCuarentena estado) {
        List<CuarentenaResponseDTO> lista = service.getAll(loteId, estado).stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuarentenaResponseDTO> getById(@PathVariable Long id) {
        Cuarentena entity = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(entity));
    }

    @GetMapping(path = { "/abierta", "/lote/{loteId}/abierta" })
    public ResponseEntity<CuarentenaResponseDTO> getCuarentenaAbierta(
            @PathVariable(required = false) Long loteId,
            @RequestParam(required = false) Long loteIdParam) {
        Long targetLoteId = loteId != null ? loteId : loteIdParam;
        Cuarentena entity = service.getCuarentenaAbiertaPorLote(targetLoteId);
        return ResponseEntity.ok(mapper.toResponseDTO(entity));
    }

    @PostMapping
    public ResponseEntity<CuarentenaResponseDTO> create(@Valid @RequestBody CuarentenaRequestDTO dto) {
        Cuarentena nuevo = service.abrirCuarentena(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
    }

    @PostMapping("/{id}/resolver")
    public ResponseEntity<CuarentenaResponseDTO> resolver(
            @PathVariable Long id,
            @Valid @RequestBody CuarentenaResolucionRequestDTO dto) {
        Cuarentena resuelta = service.resolverCuarentena(id, dto);
        return ResponseEntity.ok(mapper.toResponseDTO(resuelta));
    }

    @PostMapping("/{id}/liberar")
    public ResponseEntity<CuarentenaResponseDTO> liberar(
            @PathVariable Long id,
            @RequestBody(required = false) CuarentenaResolucionRequestDTO dto) {
        CuarentenaResolucionRequestDTO resolucionDTO = dto != null ? dto : new CuarentenaResolucionRequestDTO();
        resolucionDTO.setEstado(EstadoCuarentena.LIBERADA);
        Cuarentena resuelta = service.resolverCuarentena(id, resolucionDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(resuelta));
    }

    @PostMapping("/{id}/mantener")
    public ResponseEntity<CuarentenaResponseDTO> mantener(
            @PathVariable Long id,
            @RequestBody(required = false) CuarentenaResolucionRequestDTO dto) {
        CuarentenaResolucionRequestDTO resolucionDTO = dto != null ? dto : new CuarentenaResolucionRequestDTO();
        resolucionDTO.setEstado(EstadoCuarentena.MANTENIDA);
        Cuarentena resuelta = service.resolverCuarentena(id, resolucionDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(resuelta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuarentenaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CuarentenaRequestDTO dto) {
        Cuarentena actualizado = service.update(id, dto);
        return ResponseEntity.ok(mapper.toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
