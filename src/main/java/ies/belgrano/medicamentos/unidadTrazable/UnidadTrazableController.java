package ies.belgrano.medicamentos.unidadTrazable;

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
public class UnidadTrazableController {
	
	@Autowired
	private UnidadTrazableService service;
	
	        // Recuperar todas
		@GetMapping("/unidadTrazable")
		public ResponseEntity<List<UnidadTrazable>> buscarUnidadTrazable(){
			
			List<UnidadTrazable> listaUnidadTrazable = service.getAll();
			
			if (listaUnidadTrazable.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(listaUnidadTrazable);
			}
			
		}
		
		// Recuperar una sola
		@GetMapping("/unidadTrazable/{id}")
		public ResponseEntity<UnidadTrazable> buscarUnidadTrazablePorId(@PathVariable Long id) {
			
			UnidadTrazable unidadTrazable = service.getById(id);
			if (unidadTrazable == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(unidadTrazable);
			}
		}
		
		// Crear nueva unidadTrazable
		@PostMapping("/unidadTrazable")
		public ResponseEntity<UnidadTrazable> crearNuevaUnidadTrazable(@RequestBody UnidadTrazable unidadTrazable) {
			try {
				UnidadTrazable unidadTrazableCreada = service.create(unidadTrazable);
				return ResponseEntity.ok(unidadTrazableCreada);
			}catch(Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
		
		// Actualizar unidadTrazable
		@PutMapping("/unidadTrazable/{id}")
		public ResponseEntity<UnidadTrazable> actualizarUnidadTrazable(@PathVariable Long id, @RequestBody UnidadTrazable unidadTrazable) {
			UnidadTrazable unidadTrazableDesdeServicio = service.getById(id);
			if (unidadTrazableDesdeServicio == null) {
				return ResponseEntity.notFound().build();
			} else {
				try {
					UnidadTrazable unidadTrazableActualizado = service.update(unidadTrazable, id);
					return ResponseEntity.ok(unidadTrazableActualizado);
				}catch(Exception e) {
					return ResponseEntity.badRequest().build();
				}
			}
		}
		
		// Eliminar unidadTrazable
		@DeleteMapping("/unidadTrazable/{id}")
		public ResponseEntity<?> borrarUnidadTrazablePorId(@PathVariable Long id) {
			
			UnidadTrazable unidadTrazable = service.getById(id);
			if (unidadTrazable == null) {
				return ResponseEntity.notFound().build();
			} else {
				service.delete(id);
				return ResponseEntity.ok().build();
			}
		}

}