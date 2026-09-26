package ies.belgrano.medicamentos.inspector;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.usuario.RolUsuario;

public class InspectorANMATResponseDTO {

    private Long id;
    private Long usuarioId;
    private String usuarioEmail;
    private RolUsuario usuarioRol;
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

    public InspectorANMATResponseDTO(Long id, Long usuarioId, String usuarioEmail, RolUsuario usuarioRol,
                                    String dni, String legajoOficial, String nombreCompleto,
                                    String emailInstitucional, LocalDateTime fechaHoraAlta,
                                    String jefeAutorizador, EstadoInspector estado,
                                    LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioEmail = usuarioEmail;
        this.usuarioRol = usuarioRol;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public RolUsuario getUsuarioRol() {
        return usuarioRol;
    }

    public void setUsuarioRol(RolUsuario usuarioRol) {
        this.usuarioRol = usuarioRol;
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
