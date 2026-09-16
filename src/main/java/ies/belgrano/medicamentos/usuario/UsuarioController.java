package ies.belgrano.medicamentos.usuario;

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
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	    // Recuperar todas
		@GetMapping("/usuarios")
		public ResponseEntity<List<Usuario>> buscarUsuario(){
			
			List<Usuario> listaUsuario = service.getAll();
			
			if (listaUsuario.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(listaUsuario);
			}
			
		}
		
		// Recuperar una sola
		@GetMapping("/usuarios/{id}")
		public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
			
			Usuario usuario = service.getById(id);
			if (usuario == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(usuario);
			}
		}
		
		// Crear nuevo usuario
		@PostMapping("/usuarios")
		public ResponseEntity<Usuario> crearNuevoUsuario(@RequestBody Usuario usuario) {
			try {
				Usuario usuarioCreado = service.create(usuario);
				return ResponseEntity.ok(usuarioCreado);
			}catch(Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
		
		// Actualizar usuario
		@PutMapping("/usuarios/{id}")
		public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
			Usuario usuarioDesdeServicio = service.getById(id);
			if (usuarioDesdeServicio == null) {
				return ResponseEntity.notFound().build();
			} else {
				try {
					Usuario usuarioActualizado = service.update(usuario, id);
					return ResponseEntity.ok(usuarioActualizado);
				}catch(Exception e) {
					return ResponseEntity.badRequest().build();
				}
			}
		}
		
		// Eliminar usuario
		@DeleteMapping("/usuarios/{id}")
		public ResponseEntity<?> borrarUsuarioPorId(@PathVariable Long id) {
			
			Usuario usuario = service.getById(id);
			if (usuario == null) {
				return ResponseEntity.notFound().build();
			} else {
				service.delete(id);
				return ResponseEntity.ok().build();
			}
		}

}
