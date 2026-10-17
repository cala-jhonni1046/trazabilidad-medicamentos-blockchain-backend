package ies.belgrano.medicamentos.organizacion;

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
@RequestMapping("/api/organizaciones")
public class OrganizacionController {

    private final OrganizacionService service;
    private final OrganizacionMapper mapper;

    public OrganizacionController(OrganizacionService service, OrganizacionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<OrganizacionResponseDTO>> getAll(@RequestParam(required = false) String cuit) {
        if (cuit != null && !cuit.isBlank()) {
            Organizacion organizacion = service.getByCuit(cuit);
            return ResponseEntity.ok(List.of(mapper.toResponseDTO(organizacion)));
        }
        List<OrganizacionResponseDTO> list = service.getAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizacionResponseDTO> getById(@PathVariable Long id) {
        Organizacion organizacion = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(organizacion));
    }

    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<OrganizacionResponseDTO> getByCuit(@PathVariable String cuit) {
        Organizacion organizacion = service.getByCuit(cuit);
        return ResponseEntity.ok(mapper.toResponseDTO(organizacion));
    }

    @PostMapping
    public ResponseEntity<OrganizacionResponseDTO> create(@Valid @RequestBody OrganizacionRequestDTO requestDTO) {
        Organizacion creada = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(creada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizacionResponseDTO> update(@PathVariable Long id,
                                                          @Valid @RequestBody OrganizacionRequestDTO requestDTO) {
        Organizacion actualizada = service.update(id, requestDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
