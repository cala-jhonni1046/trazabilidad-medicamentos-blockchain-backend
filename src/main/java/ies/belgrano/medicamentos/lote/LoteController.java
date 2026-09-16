package ies.belgrano.medicamentos.lote;

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
public class LoteController {
	
	@Autowired
	private LoteService service;
	
	        // Recuperar todas
		@GetMapping("/lotes")
		public ResponseEntity<List<Lote>> buscarLote(){
			
			List<Lote> listaLote = service.getAll();
			
			if (listaLote.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(listaLote);
			}
			
		}
		
		// Recuperar una sola
		@GetMapping("/lotes/{id}")
		public ResponseEntity<Lote> buscarLotePorId(@PathVariable Long id) {
			
			Lote lote = service.getById(id);
			if (lote == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(lote);
			}
		}
		
		// Crear nuevo lote
		@PostMapping("/lotes")
		public ResponseEntity<Lote> crearNuevoLote(@RequestBody Lote lote) {
			try {
				Lote loteCreado = service.create(lote);
				return ResponseEntity.ok(loteCreado);
			}catch(Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
		
		// Actualizar lote
		@PutMapping("/lotes/{id}")
		public ResponseEntity<Lote> actualizarLote(@PathVariable Long id, @RequestBody Lote lote) {
			Lote loteDesdeServicio = service.getById(id);
			if (loteDesdeServicio == null) {
				return ResponseEntity.notFound().build();
			} else {
				try {
					Lote loteActualizado = service.update(lote, id);
					return ResponseEntity.ok(loteActualizado);
				}catch(Exception e) {
					return ResponseEntity.badRequest().build();
				}
			}
		}
		
		// Eliminar lote
		@DeleteMapping("/lotes/{id}")
		public ResponseEntity<?> borrarLotePorId(@PathVariable Long id) {
			
			Lote lote = service.getById(id);
			if (lote == null) {
				return ResponseEntity.notFound().build();
			} else {
				service.delete(id);
				return ResponseEntity.ok().build();
			}
		}

}
