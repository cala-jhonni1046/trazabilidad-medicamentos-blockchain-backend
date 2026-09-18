package ies.belgrano.medicamentos.inspector;

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
@RequestMapping("/api/inspectores")
public class InspectorANMATController {
	
	@Autowired
	private InspectorANMATService service;

	@Autowired
	private InspectorANMATMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<InspectorANMATResponseDTO>> getAll() {
		List<InspectorANMAT> inspectores = service.getAll();
		
		if (inspectores.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			List<InspectorANMATResponseDTO> dtoList = inspectores.stream()
					.map(mapper::toResponseDTO)
					.toList();
			return ResponseEntity.ok(dtoList);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<InspectorANMATResponseDTO> getById(@PathVariable Long id) {
		InspectorANMAT inspectorANMAT = service.getById(id);
		if (inspectorANMAT == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(mapper.toResponseDTO(inspectorANMAT));
		}
	}
	
	@PostMapping
	public ResponseEntity<InspectorANMATResponseDTO> create(@RequestBody InspectorANMATRequestDTO requestDTO) {
		try {
			InspectorANMAT entity = mapper.toEntity(requestDTO);
			InspectorANMAT inspectorANMATCreado = service.create(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(inspectorANMATCreado));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<InspectorANMATResponseDTO> update(@PathVariable Long id, @RequestBody InspectorANMATRequestDTO requestDTO) {
		InspectorANMAT inspectorExistente = service.getById(id);
		if (inspectorExistente == null) {
			return ResponseEntity.notFound().build();
		} else {
			try {
				InspectorANMAT entity = mapper.toEntity(requestDTO);
				InspectorANMAT inspectorANMATActualizado = service.update(id, entity);
				return ResponseEntity.ok(mapper.toResponseDTO(inspectorANMATActualizado));
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		InspectorANMAT inspectorANMAT = service.getById(id);
		if (inspectorANMAT == null) {
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
