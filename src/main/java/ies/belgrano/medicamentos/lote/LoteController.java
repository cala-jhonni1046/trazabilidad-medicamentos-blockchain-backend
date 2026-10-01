package ies.belgrano.medicamentos.lote;

import java.util.List;

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

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

	@Autowired
	private LoteService service;

	@Autowired
	private LoteMapper mapper;

	@GetMapping
	public ResponseEntity<List<LoteResponseDTO>> getAll() {
		List<Lote> lista = service.getAll();
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			List<LoteResponseDTO> dtoList = lista.stream()
					.map(mapper::toResponseDTO)
					.toList();
			return ResponseEntity.ok(dtoList);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<LoteResponseDTO> getById(@PathVariable Long id) {
		Lote lote = service.getById(id);
		if (lote == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(mapper.toResponseDTO(lote));
		}
	}

	@PostMapping
	public ResponseEntity<LoteResponseDTO> create(@RequestBody LoteRequestDTO requestDTO) {
		try {
			Lote entity = mapper.toEntity(requestDTO);
			Lote loteCreado = service.create(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(loteCreado));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<LoteResponseDTO> update(@PathVariable Long id, @RequestBody LoteRequestDTO requestDTO) {
		Lote existente = service.getById(id);
		if (existente == null) {
			return ResponseEntity.notFound().build();
		}
		try {
			Lote entity = mapper.toEntity(requestDTO);
			Lote loteActualizado = service.update(id, entity);
			return ResponseEntity.ok(mapper.toResponseDTO(loteActualizado));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Lote lote = service.getById(id);
		if (lote == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
	}
}
