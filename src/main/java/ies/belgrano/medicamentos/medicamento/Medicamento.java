package ies.belgrano.medicamentos.medicamento;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Medicamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String principioActivo;
	private boolean requiereCadenaFrio;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrincipioActivo() {
		return principioActivo;
	}
	public void setPrincipioActivo(String principioActivo) {
		this.principioActivo = principioActivo;
	}
	public boolean isRequiereCadenaFrio() {
		return requiereCadenaFrio;
	}
	public void setRequiereCadenaFrio(boolean requiereCadenaFrio) {
		this.requiereCadenaFrio = requiereCadenaFrio;
	}
	
	

}
