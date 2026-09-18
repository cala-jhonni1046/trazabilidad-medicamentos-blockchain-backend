package ies.belgrano.medicamentos.enlaceCUIT;

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
@RequestMapping("/api/enlaces-cuit")
public class EnlaceCUITController {
	
	@Autowired
	private EnlaceCUITService service;

	@Autowired
	private EnlaceCUITMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<EnlaceCUITResponseDTO>> getAll() {
		List<EnlaceCUIT> enlaces = service.getAll();
		
		if (enlaces.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			List<EnlaceCUITResponseDTO> dtoList = enlaces.stream()
					.map(mapper::toResponseDTO)
					.toList();
			return ResponseEntity.ok(dtoList);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EnlaceCUITResponseDTO> getById(@PathVariable Long id) {
		EnlaceCUIT enlaceCUIT = service.getById(id);
		if (enlaceCUIT == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(mapper.toResponseDTO(enlaceCUIT));
		}
	}
	
	@PostMapping
	public ResponseEntity<EnlaceCUITResponseDTO> create(@RequestBody EnlaceCUITRequestDTO requestDTO) {
		try {
			EnlaceCUIT entity = mapper.toEntity(requestDTO);
			EnlaceCUIT enlaceCUITCreado = service.create(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(enlaceCUITCreado));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EnlaceCUITResponseDTO> update(@PathVariable Long id, @RequestBody EnlaceCUITRequestDTO requestDTO) {
		EnlaceCUIT enlaceExistente = service.getById(id);
		if (enlaceExistente == null) {
			return ResponseEntity.notFound().build();
		} else {
			try {
				EnlaceCUIT entity = mapper.toEntity(requestDTO);
				EnlaceCUIT enlaceCUITActualizado = service.update(id, entity);
				return ResponseEntity.ok(mapper.toResponseDTO(enlaceCUITActualizado));
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		EnlaceCUIT enlaceCUIT = service.getById(id);
		if (enlaceCUIT == null) {
			return ResponseEntity.notFound().build();
		} else {
			try {
				service.delete(id);
				return ResponseEntity.noContent().build();
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}
}
