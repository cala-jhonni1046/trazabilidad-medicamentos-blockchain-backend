package ies.belgrano.medicamentos.enlaceCUIT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnlaceCUITService {
	
	@Autowired
	private EnlaceCUITRepository repo;
	
	
	public List<EnlaceCUIT> getAll(){
		return repo.findAll();
	}
	
	public EnlaceCUIT getById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public EnlaceCUIT create(EnlaceCUIT enlaceCUIT) {
		return repo.save(enlaceCUIT);
	}
	
	public EnlaceCUIT update(Long id, EnlaceCUIT enlaceCUIT) {
		EnlaceCUIT actualizarEnlaceCUIT = this.getById(id);
		if(actualizarEnlaceCUIT == null) {
			return null;
		} else {
			enlaceCUIT.setId(id);
			return repo.save(enlaceCUIT);
		}
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
