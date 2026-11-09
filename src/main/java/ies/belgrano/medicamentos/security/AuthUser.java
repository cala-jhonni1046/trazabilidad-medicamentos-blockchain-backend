package ies.belgrano.medicamentos.security;

import java.io.Serializable;

import ies.belgrano.medicamentos.usuario.RolUsuario;

public class AuthUser implements Serializable {

    private final Long id;
    private final String email;
    private final RolUsuario rol;
    private final Long organizacionId;

    public AuthUser(Long id, String email, RolUsuario rol, Long organizacionId) {
        this.id = id;
        this.email = email;
        this.rol = rol;
        this.organizacionId = organizacionId;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public Long getOrganizacionId() {
        return organizacionId;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", rol=" + rol +
                ", organizacionId=" + organizacionId +
                '}';
    }
}

