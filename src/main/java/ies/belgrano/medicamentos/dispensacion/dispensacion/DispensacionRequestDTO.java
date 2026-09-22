package ies.belgrano.medicamentos.operaciones.dispensacion;

import java.time.LocalDateTime;

public class DispensacionRequestDTO {

    private String recetaNumero;
    private String codigoQR;
    private String dniPaciente;
    private String farmaciaCuit;
    private LocalDateTime fechaDispensacion;

    public DispensacionRequestDTO() {
    }

    public DispensacionRequestDTO(String recetaNumero, String codigoQR, String dniPaciente, String farmaciaCuit, LocalDateTime fechaDispensacion) {
        this.recetaNumero = recetaNumero;
        this.codigoQR = codigoQR;
        this.dniPaciente = dniPaciente;
        this.farmaciaCuit = farmaciaCuit;
        this.fechaDispensacion = fechaDispensacion;
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
}
