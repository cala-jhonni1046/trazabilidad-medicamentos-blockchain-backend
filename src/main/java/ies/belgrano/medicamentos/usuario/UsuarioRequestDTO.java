package ies.belgrano.medicamentos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El formato de email no es válido.")
    @Size(max = 180, message = "El email no debe superar los 180 caracteres.")
    private String email;

    @NotNull(message = "El rol es obligatorio.")
    private RolUsuario rol;

    private Boolean activo;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String password;

    private Long organizacionId;

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(String email, RolUsuario rol, Boolean activo) {
        this.email = email;
        this.rol = rol;
        this.activo = activo;
    }

    public UsuarioRequestDTO(String email, RolUsuario rol, Boolean activo, String password, Long organizacionId) {
        this.email = email;
        this.rol = rol;
        this.activo = activo;
        this.password = password;
        this.organizacionId = organizacionId;
    }

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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getOrganizacionId() {
        return organizacionId;
    }

    public void setOrganizacionId(Long organizacionId) {
        this.organizacionId = organizacionId;
    }
}
