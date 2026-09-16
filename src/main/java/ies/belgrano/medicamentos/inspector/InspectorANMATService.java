package ies.belgrano.medicamentos.inspector;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class InspectorANMATService {
	
	@Autowired
	private InspectorANMATRepository repo;
	
	
	public List<InspectorANMAT> getAll(){
		return repo.findAll();
	}
	
	public InspectorANMAT getById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public InspectorANMAT create(InspectorANMAT inspectorANMAT) {
		return repo.save(inspectorANMAT);
	}
	
	public InspectorANMAT update(InspectorANMAT inspectorANMAT, Long id) {
		InspectorANMAT actualizarInspectorANMAT = this.getById(id);
		if(actualizarInspectorANMAT == null) {
			return null;
		} else {
			inspectorANMAT.setId(id);
			return repo.save(inspectorANMAT);
		}
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
