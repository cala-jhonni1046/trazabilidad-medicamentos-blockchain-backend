package ies.belgrano.medicamentos.inspector;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.usuario.Usuario;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "inspectores_anmat")
public class InspectorANMAT extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name = "usuario_id", nullable = false, unique = true)
	private Usuario usuario;
	
	@Column(name = "dni", length = 16)
	private String dni;
	@Column(name = "legajo_oficial", unique = true, length = 80)
	private String legajoOficial;
	@Column(name = "nombre_completo", nullable = false, length = 160)
	private String nombreCompleto;
	@Column(name = "email_institucional", length = 180)
	private String emailInstitucional;
	@Column(name = "fecha_hora_alta_utc")
	private LocalDateTime fechaHoraAlta;
	@Column(name = "jefe_autorizador", length = 160)
	private String jefeAutorizador;
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false, length = 24)
	private EstadoInspector estado;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	
	public EstadoInspector getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoInspector estado) {
		this.estado = estado;
	}
	

}
