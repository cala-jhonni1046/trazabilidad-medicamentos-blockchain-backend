package ies.belgrano.medicamentos.blockchain.registroblockchain;

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

@RestController
@RequestMapping("/api/registros-blockchain")
public class RegistroBlockchainController {

    @Autowired
    private RegistroBlockchainService service;

    @Autowired
    private RegistroBlockchainMapper mapper;

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
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toResponseDTO(registro));
    }

    @PostMapping
    public ResponseEntity<RegistroBlockchainResponseDTO> create(@RequestBody RegistroBlockchainRequestDTO dto) {
        try {
            RegistroBlockchain nuevo = service.create(mapper.toEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(nuevo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroBlockchainResponseDTO> update(@PathVariable Long id, @RequestBody RegistroBlockchainRequestDTO dto) {
        try {
            RegistroBlockchain actualizado = service.update(id, mapper.toEntity(dto));
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(mapper.toResponseDTO(actualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        RegistroBlockchain registro = service.getById(id);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
