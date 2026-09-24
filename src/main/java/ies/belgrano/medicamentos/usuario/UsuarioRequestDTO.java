package ies.belgrano.medicamentos.usuario;

public class UsuarioRequestDTO {

    private String email;
    private String passwordHash;
    private RolUsuario rol;
    private Boolean activo;

    public UsuarioRequestDTO() {
    }

    public UsuarioRequestDTO(String email, String passwordHash, RolUsuario rol, Boolean activo) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.activo = activo;
    }

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
