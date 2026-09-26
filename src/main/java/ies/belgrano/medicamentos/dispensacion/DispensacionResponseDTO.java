package ies.belgrano.medicamentos.operaciones.dispensacion;

import java.time.LocalDateTime;

public class DispensacionResponseDTO {

    private Long id;
    private String recetaNumero;
    private String codigoQR;
    private String dniPaciente;
    private String farmaciaCuit;
    private LocalDateTime fechaDispensacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public DispensacionResponseDTO() {
    }

    public DispensacionResponseDTO(Long id, String recetaNumero, String codigoQR, String dniPaciente, String farmaciaCuit, LocalDateTime fechaDispensacion, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.recetaNumero = recetaNumero;
        this.codigoQR = codigoQR;
        this.dniPaciente = dniPaciente;
        this.farmaciaCuit = farmaciaCuit;
        this.fechaDispensacion = fechaDispensacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecetaNumero() {
        return recetaNumero;
    }

    public void setRecetaNumero(String recetaNumero) {
        this.recetaNumero = recetaNumero;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getFarmaciaCuit() {
        return farmaciaCuit;
    }

    public void setFarmaciaCuit(String farmaciaCuit) {
        this.farmaciaCuit = farmaciaCuit;
    }

    public LocalDateTime getFechaDispensacion() {
        return fechaDispensacion;
    }

    public void setFechaDispensacion(LocalDateTime fechaDispensacion) {
        this.fechaDispensacion = fechaDispensacion;
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
