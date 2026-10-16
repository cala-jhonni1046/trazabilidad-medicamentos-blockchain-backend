package ies.belgrano.medicamentos.usuario;

import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity {

	@Column(name = "email", nullable = false, unique = true, length = 180)
	private String email;
	@Enumerated(EnumType.STRING)
	@Column(name = "rol", nullable = false, length = 24)
	private RolUsuario rol;
	@Column(name = "activo", nullable = false)
	private boolean activo;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
