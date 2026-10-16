package ies.belgrano.medicamentos.medicamento;

import java.time.LocalDateTime;

public class MedicamentoResponseDTO {

    private Long id;
    private String nombre;
    private String principioActivo;
    private Boolean requiereCadenaFrio;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public MedicamentoResponseDTO() {
    }

    public MedicamentoResponseDTO(Long id, String nombre, String principioActivo, Boolean requiereCadenaFrio,
                                  LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.principioActivo = principioActivo;
        this.requiereCadenaFrio = requiereCadenaFrio;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public Boolean getRequiereCadenaFrio() {
        return requiereCadenaFrio;
    }

    public void setRequiereCadenaFrio(Boolean requiereCadenaFrio) {
        this.requiereCadenaFrio = requiereCadenaFrio;
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
