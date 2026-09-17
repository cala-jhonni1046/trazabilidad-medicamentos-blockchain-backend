package ies.belgrano.medicamentos.dispensacion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dispensaciones")
public class Dispensacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String recetaNumero;

    private String codigoQR;

    private String dniPaciente;

    private String farmaciaCuit;

    private LocalDateTime fechaDispensacion;

    public Dispensacion() {
    }

    public Dispensacion(UUID id, String recetaNumero, String codigoQR, String dniPaciente, String farmaciaCuit, LocalDateTime fechaDispensacion) {
        this.id = id;
        this.recetaNumero = recetaNumero;
        this.codigoQR = codigoQR;
        this.dniPaciente = dniPaciente;
        this.farmaciaCuit = farmaciaCuit;
        this.fechaDispensacion = fechaDispensacion;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
}
