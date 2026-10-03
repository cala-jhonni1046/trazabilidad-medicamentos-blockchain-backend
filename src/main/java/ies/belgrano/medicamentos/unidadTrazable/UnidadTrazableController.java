package ies.belgrano.medicamentos.unidadTrazable;

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
@RequestMapping("/api/unidades-trazables")
public class UnidadTrazableController {

	@Autowired
	private UnidadTrazableService service;

	@Autowired
	private UnidadTrazableMapper mapper;

	@GetMapping
	public ResponseEntity<List<UnidadTrazableResponseDTO>> getAll() {
		List<UnidadTrazable> lista = service.getAll();
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			List<UnidadTrazableResponseDTO> dtoList = lista.stream()
					.map(mapper::toResponseDTO)
					.toList();
			return ResponseEntity.ok(dtoList);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<UnidadTrazableResponseDTO> getById(@PathVariable Long id) {
		UnidadTrazable unidadTrazable = service.getById(id);
		if (unidadTrazable == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(mapper.toResponseDTO(unidadTrazable));
		}
	}

	@PostMapping
	public ResponseEntity<UnidadTrazableResponseDTO> create(@RequestBody UnidadTrazableRequestDTO requestDTO) {
		try {
			UnidadTrazable entity = mapper.toEntity(requestDTO);
			UnidadTrazable unidadTrazableCreada = service.create(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(unidadTrazableCreada));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<UnidadTrazableResponseDTO> update(@PathVariable Long id, @RequestBody UnidadTrazableRequestDTO requestDTO) {
		UnidadTrazable existente = service.getById(id);
		if (existente == null) {
			return ResponseEntity.notFound().build();
		}
		try {
			UnidadTrazable entity = mapper.toEntity(requestDTO);
			UnidadTrazable unidadTrazableActualizada = service.update(id, entity);
			return ResponseEntity.ok(mapper.toResponseDTO(unidadTrazableActualizada));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		UnidadTrazable unidadTrazable = service.getById(id);
		if (unidadTrazable == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
	}
}