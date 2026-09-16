package ies.belgrano.medicamentos.inspector;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InspectorANMAT {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String dni;
	private String legajoOficial;
	private String nombreCompleto;
	private String emailInstitucional;
	private LocalDateTime fechaHoraAlta;
	private String jefeAutorizador;
	private String estado;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getLegajoOficial() {
		return legajoOficial;
	}
	
	public void setLegajoOficial(String legajoOficial) {
		this.legajoOficial = legajoOficial;
	}
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	public String getEmailInstitucional() {
		return emailInstitucional;
	}
	
	public void setEmailInstitucional(String emailInstitucional) {
		this.emailInstitucional = emailInstitucional;
	}
	
	public LocalDateTime getFechaHoraAlta() {
		return fechaHoraAlta;
	}
	
	public void setFechaHoraAlta(LocalDateTime fechaHoraAlta) {
		this.fechaHoraAlta = fechaHoraAlta;
	}
	
	public String getJefeAutorizador() {
		return jefeAutorizador;
	}
	
	public void setJefeAutorizador(String jefeAutorizador) {
		this.jefeAutorizador = jefeAutorizador;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	

}
