package ies.belgrano.medicamentos.inspector;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InspectorANMATRequestDTO {

    @NotNull(message = "El usuarioId es obligatorio.")
    private Long usuarioId;

    @Size(max = 16, message = "El DNI no debe superar los 16 caracteres.")
    private String dni;

    @Size(max = 80, message = "El legajo oficial no debe superar los 80 caracteres.")
    private String legajoOficial;

    @NotBlank(message = "El nombre completo es obligatorio.")
    @Size(max = 160, message = "El nombre completo no debe superar los 160 caracteres.")
    private String nombreCompleto;

    @Size(max = 180, message = "El email institucional no debe superar los 180 caracteres.")
    @Email(message = "El formato de email institucional no es válido.")
    private String emailInstitucional;

    private LocalDateTime fechaHoraAlta;

    @Size(max = 160, message = "El jefe autorizador no debe superar los 160 caracteres.")
    private String jefeAutorizador;

    private EstadoInspector estado;

    public InspectorANMATRequestDTO() {
    }

    public InspectorANMATRequestDTO(Long usuarioId,
                                   String dni, String legajoOficial, String nombreCompleto,
                                   String emailInstitucional, LocalDateTime fechaHoraAlta,
                                   String jefeAutorizador, EstadoInspector estado) {
        this.usuarioId = usuarioId;
        this.dni = dni;
        this.legajoOficial = legajoOficial;
        this.nombreCompleto = nombreCompleto;
        this.emailInstitucional = emailInstitucional;
        this.fechaHoraAlta = fechaHoraAlta;
        this.jefeAutorizador = jefeAutorizador;
        this.estado = estado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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
