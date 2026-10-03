package ies.belgrano.medicamentos.medicamento;

import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class Medicamento extends BaseEntity {
	private String nombre;
	private String principioActivo;
	private boolean requiereCadenaFrio;
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
