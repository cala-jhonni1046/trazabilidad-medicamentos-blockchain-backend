package ies.belgrano.medicamentos.inspector;

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
public class InspectorANMATController {
	
	@Autowired
	private InspectorANMATService service;
	
	    // Recuperar todas
		@GetMapping("/inspectorANMAT")
		public ResponseEntity<List<InspectorANMAT>> buscarInspectorANMAT(){
			
			List<InspectorANMAT> listaInspectorANMAT = service.getAll();
			
			if (listaInspectorANMAT.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(listaInspectorANMAT);
			}
			
		}
		
		// Recuperar una sola
		@GetMapping("/inspectorANMAT/{id}")
		public ResponseEntity<InspectorANMAT> buscarInspectorANMATPorId(@PathVariable Long id) {
			
			InspectorANMAT inspectorANMAT = service.getById(id);
			if (inspectorANMAT == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(inspectorANMAT);
			}
		}
		
		// Crear nuevo inspectorANMAT
		@PostMapping("/inspectorANMAT")
		public ResponseEntity<InspectorANMAT> crearNuevoInspectorANMAT(@RequestBody InspectorANMAT inspectorANMAT) {
			try {
				InspectorANMAT inspectorANMATCreado = service.create(inspectorANMAT);
				return ResponseEntity.ok(inspectorANMATCreado);
			}catch(Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
		
		// Actualizar inspectorANMAT
		@PutMapping("/inspectorANMAT/{id}")
		public ResponseEntity<InspectorANMAT> actualizarInspectorANMAT(@PathVariable Long id, @RequestBody InspectorANMAT inspectorANMAT) {
			InspectorANMAT inspectorANMATDesdeServicio = service.getById(id);
			if (inspectorANMATDesdeServicio == null) {
				return ResponseEntity.notFound().build();
			} else {
				try {
					InspectorANMAT inspectorANMATActualizado = service.update(inspectorANMAT, id);
					return ResponseEntity.ok(inspectorANMATActualizado);
				}catch(Exception e) {
					return ResponseEntity.badRequest().build();
				}
			}
		}
		
		// Eliminar inspectorANMAT
		@DeleteMapping("/inspectorANMAT/{id}")
		public ResponseEntity<?> borrarInspectorANMATPorId(@PathVariable Long id) {
			
			InspectorANMAT inspectorANMAT = service.getById(id);
			if (inspectorANMAT == null) {
				return ResponseEntity.notFound().build();
			} else {
				service.delete(id);
				return ResponseEntity.ok().build();
			}
		}

}
