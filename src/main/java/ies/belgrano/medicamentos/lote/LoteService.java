package ies.belgrano.medicamentos.lote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoteService {
	
	@Autowired
	private LoteRepository repo;
	
	
	public List<Lote> getAll(){
		return repo.findAll();
	}
	
	public Lote getById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public Lote create(Lote lote) {
		return repo.save(lote);
	}
	
	public Lote update(Long id, Lote lote) {
		Lote actualizarLote = this.getById(id);
		if(actualizarLote == null) {
			return null;
		} else {
			lote.setId(id);
			return repo.save(lote);
		}
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}