package ies.belgrano.medicamentos.medicamento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class MedicamentoController {
	
	@Autowired
	private MedicamentoService service;
	
	        // Recuperar todas
		@GetMapping("/medicamentos")
		public ResponseEntity<List<Medicamento>> buscarMedicamento(){
			
			List<Medicamento> listaMedicamento = service.getAll();
			
			if (listaMedicamento.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(listaMedicamento);
			}
			
		}
		
		// Recuperar una sola
		@GetMapping("/medicamentos/{id}")
		public ResponseEntity<Medicamento> buscarMedicamentoPorId(@PathVariable Long id) {
			
			Medicamento medicamento = service.getById(id);
			if (medicamento == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(medicamento);
			}
		}
		
		// Crear nuevo medicamento
		@PostMapping("/medicamentos")
		public ResponseEntity<Medicamento> crearNuevoMedicamento(@RequestBody Medicamento medicamento) {
			try {
				Medicamento medicamentoCreado = service.create(medicamento);
				return ResponseEntity.ok(medicamentoCreado);
			}catch(Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
		
		// Actualizar medicamento
		@PutMapping("/medicamentos/{id}")
		public ResponseEntity<Medicamento> actualizarMedicamento(@PathVariable Long id, @RequestBody Medicamento medicamento) {
			Medicamento medicamentoDesdeServicio = service.getById(id);
			if (medicamentoDesdeServicio == null) {
				return ResponseEntity.notFound().build();
			} else {
				try {
					Medicamento medicamentoActualizado = service.update(medicamento, id);
					return ResponseEntity.ok(medicamentoActualizado);
				}catch(Exception e) {
					return ResponseEntity.badRequest().build();
				}
			}
		}
		
		// Eliminar medicamento
		@DeleteMapping("/medicamentos/{id}")
		public ResponseEntity<?> borrarMedicamentoPorId(@PathVariable Long id) {
			
			Medicamento medicamento = service.getById(id);
			if (medicamento == null) {
				return ResponseEntity.notFound().build();
			} else {
				service.delete(id);
				return ResponseEntity.ok().build();
			}
		}

}