package ies.belgrano.medicamentos.usuario;

import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class Usuario extends BaseEntity {

	private String email;
	private String passwordHash;
	private String rol;
	private boolean activo;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
