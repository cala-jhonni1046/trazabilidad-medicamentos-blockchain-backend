package ies.belgrano.medicamentos.inspector;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.usuario.RolUsuario;

public class InspectorANMATResponseDTO {

    private Long id;
    private String email;
    private RolUsuario rol;
    private Boolean activo;
    private String dni;
    private String legajoOficial;
    private String nombreCompleto;
    private String emailInstitucional;
    private LocalDateTime fechaHoraAlta;
    private String jefeAutorizador;
    private EstadoInspector estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public InspectorANMATResponseDTO() {
    }

    public InspectorANMATResponseDTO(Long id, String email, RolUsuario rol, Boolean activo,
                                    String dni, String legajoOficial, String nombreCompleto,
                                    String emailInstitucional, LocalDateTime fechaHoraAlta,
                                    String jefeAutorizador, EstadoInspector estado,
                                    LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.email = email;
        this.rol = rol;
        this.activo = activo;
        this.dni = dni;
        this.legajoOficial = legajoOficial;
        this.nombreCompleto = nombreCompleto;
        this.emailInstitucional = emailInstitucional;
        this.fechaHoraAlta = fechaHoraAlta;
        this.jefeAutorizador = jefeAutorizador;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
