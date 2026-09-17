package ies.belgrano.medicamentos.unidadTrazable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnidadTrazableService {
	
	@Autowired
	private UnidadTrazableRepository repo;
	
	
	public List<UnidadTrazable> getAll(){
		return repo.findAll();
	}
	
	public UnidadTrazable getById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public UnidadTrazable create(UnidadTrazable unidadTrazable) {
		return repo.save(unidadTrazable);
	}
	
	public UnidadTrazable update(UnidadTrazable unidadTrazable, Long id) {
		UnidadTrazable actualizarUnidadTrazable = this.getById(id);
		if(actualizarUnidadTrazable == null) {
			return null;
		} else {
			unidadTrazable.setId(id);
			return repo.save(unidadTrazable);
		}
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}