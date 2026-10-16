package ies.belgrano.medicamentos.usuario;

public class UsuarioRequestDTO {

    private String email;
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
