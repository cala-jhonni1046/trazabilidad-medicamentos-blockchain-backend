package ies.belgrano.medicamentos.enlaceCUIT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class EnlaceCUITController {
	
	@Autowired
	private EnlaceCUITService service;
	
	    // Recuperar todas
		@GetMapping("/enlaceCUIT")
		public ResponseEntity<List<EnlaceCUIT>> buscarEnlaceCUIT(){
			
			List<EnlaceCUIT> listaEnlaceCUIT = service.getAll();
			
			if (listaEnlaceCUIT.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(listaEnlaceCUIT);
			}
			
		}
		
		// Recuperar una sola
		@GetMapping("/enlaceCUIT/{id}")
		public ResponseEntity<EnlaceCUIT> buscarEnlaceCUITPorId(@PathVariable Long id) {
			
			EnlaceCUIT enlaceCUIT = service.getById(id);
			if (enlaceCUIT == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(enlaceCUIT);
			}
		}
		
		// Crear nuevo enlaceCUIT
		@PostMapping("/enlaceCUIT")
		public ResponseEntity<EnlaceCUIT> crearNuevoEnlaceCUIT(@RequestBody EnlaceCUIT enlaceCUIT) {
			try {
				EnlaceCUIT enlaceCUITCreado = service.create(enlaceCUIT);
				return ResponseEntity.ok(enlaceCUITCreado);
			}catch(Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
		
		// Actualizar enlaceCUIT
		@PutMapping("/enlaceCUIT/{id}")
		public ResponseEntity<EnlaceCUIT> actualizarEnlaceCUIT(@PathVariable Long id, @RequestBody EnlaceCUIT enlaceCUIT) {
			EnlaceCUIT enlaceCUITDesdeServicio = service.getById(id);
			if (enlaceCUITDesdeServicio == null) {
				return ResponseEntity.notFound().build();
			} else {
				try {
					EnlaceCUIT enlaceCUITActualizado = service.update(enlaceCUIT, id);
					return ResponseEntity.ok(enlaceCUITActualizado);
				}catch(Exception e) {
					return ResponseEntity.badRequest().build();
				}
			}
		}
		
		// Eliminar enlaceCUIT
		@DeleteMapping("/enlaceCUIT/{id}")
		public ResponseEntity<?> borrarEnlaceCUITPorId(@PathVariable Long id) {
			
			EnlaceCUIT enlaceCUIT = service.getById(id);
			if (enlaceCUIT == null) {
				return ResponseEntity.notFound().build();
			} else {
				service.delete(id);
				return ResponseEntity.ok().build();
			}
		}

}
