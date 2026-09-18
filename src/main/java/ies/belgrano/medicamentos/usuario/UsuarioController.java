package ies.belgrano.medicamentos.usuario;

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
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;

	@Autowired
	private UsuarioMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
		List<Usuario> usuarios = service.getAll();
		
		if (usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			List<UsuarioResponseDTO> dtoList = usuarios.stream()
					.map(mapper::toResponseDTO)
					.toList();
			return ResponseEntity.ok(dtoList);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
		Usuario usuario = service.getById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(mapper.toResponseDTO(usuario));
		}
	}
	
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> create(@RequestBody UsuarioRequestDTO requestDTO) {
		try {
			Usuario entity = mapper.toEntity(requestDTO);
			Usuario usuarioCreado = service.create(entity);
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(usuarioCreado));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @RequestBody UsuarioRequestDTO requestDTO) {
		Usuario usuarioExistente = service.getById(id);
		if (usuarioExistente == null) {
			return ResponseEntity.notFound().build();
		} else {
			try {
				Usuario entity = mapper.toEntity(requestDTO);
				Usuario usuarioActualizado = service.update(id, entity);
				return ResponseEntity.ok(mapper.toResponseDTO(usuarioActualizado));
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Usuario usuario = service.getById(id);
		if (usuario == null) {
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
