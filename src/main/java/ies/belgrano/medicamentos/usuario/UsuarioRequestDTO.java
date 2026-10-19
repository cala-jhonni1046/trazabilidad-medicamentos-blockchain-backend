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

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(String email, RolUsuario rol, Boolean activo) {
        this.email = email;
        this.rol = rol;
        this.activo = activo;
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
}
