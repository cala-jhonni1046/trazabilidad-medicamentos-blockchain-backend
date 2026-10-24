package ies.belgrano.medicamentos.blockchain.registroblockchain;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/registros-blockchain")
@Tag(name = "Registros demostrativos", description = "CRUD de metadatos declarados. No existe conexión con una blockchain distribuida ni verificación criptográfica de firmas.")
public class RegistroBlockchainController {

    private final RegistroBlockchainService service;
    private final RegistroBlockchainMapper mapper;

    public RegistroBlockchainController(RegistroBlockchainService service, RegistroBlockchainMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<RegistroBlockchainResponseDTO>> getAll() {
        List<RegistroBlockchainResponseDTO> lista = service.getAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroBlockchainResponseDTO> getById(@PathVariable Long id) {
        RegistroBlockchain registro = service.getById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(registro));
    }

    @PostMapping
    public ResponseEntity<RegistroBlockchainResponseDTO> create(@Valid @RequestBody RegistroBlockchainRequestDTO dto) {
        RegistroBlockchain nuevo = service.create(mapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroBlockchainResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RegistroBlockchainRequestDTO dto) {
        RegistroBlockchain actualizado = service.update(id, mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
