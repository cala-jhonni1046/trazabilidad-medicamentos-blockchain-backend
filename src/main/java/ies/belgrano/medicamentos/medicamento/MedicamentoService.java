package ies.belgrano.medicamentos.medicamento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoService {
	
	@Autowired
	private MedicamentoRepository repo;
	
	
	public List<Medicamento> getAll(){
		return repo.findAll();
	}
	
	public Medicamento getById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public Medicamento create(Medicamento medicamento) {
		return repo.save(medicamento);
	}
	
	public Medicamento update(Medicamento medicamento, Long id) {
		Medicamento actualizarMedicamento = this.getById(id);
		if(actualizarMedicamento == null) {
			return null;
		} else {
			medicamento.setId(id);
			return repo.save(medicamento);
		}
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}


