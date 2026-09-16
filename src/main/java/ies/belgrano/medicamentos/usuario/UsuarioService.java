package ies.belgrano.medicamentos.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	
	public List<Usuario> getAll(){
		return repo.findAll();
	}
	
	public Usuario getById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public Usuario create(Usuario usuario) {
		return repo.save(usuario);
	}
	
	public Usuario update(Usuario usuario, Long id) {
		Usuario actualizarUsuario = this.getById(id);
		if(actualizarUsuario == null) {
			return null;
		} else {
			usuario.setId(id);
			return repo.save(usuario);
		}
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
