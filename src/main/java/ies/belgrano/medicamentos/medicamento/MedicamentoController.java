package ies.belgrano.medicamentos.medicamento;

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
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

	@Autowired
	private MedicamentoService service;

	@Autowired
	private MedicamentoMapper mapper;

	@GetMapping
	public ResponseEntity<List<MedicamentoResponseDTO>> getAll() {
		List<Medicamento> lista = service.getAll();
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			List<MedicamentoResponseDTO> dtoList = lista.stream()
					.map(mapper::toResponseDTO)
					.toList();
			return ResponseEntity.ok(dtoList);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicamentoResponseDTO> getById(@PathVariable Long id) {
		Medicamento medicamento = service.getById(id);
		if (medicamento == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(mapper.toResponseDTO(medicamento));
		}
	}

	@PostMapping
	public ResponseEntity<MedicamentoResponseDTO> create(@RequestBody MedicamentoRequestDTO requestDTO) {
		try {
			Medicamento entity = mapper.toEntity(requestDTO);
			Medicamento medicamentoCreado = service.create(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(medicamentoCreado));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<MedicamentoResponseDTO> update(@PathVariable Long id, @RequestBody MedicamentoRequestDTO requestDTO) {
		Medicamento existente = service.getById(id);
		if (existente == null) {
			return ResponseEntity.notFound().build();
		}
		try {
			Medicamento entity = mapper.toEntity(requestDTO);
			Medicamento medicamentoActualizado = service.update(id, entity);
			return ResponseEntity.ok(mapper.toResponseDTO(medicamentoActualizado));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Medicamento medicamento = service.getById(id);
		if (medicamento == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
	}
}