package ies.belgrano.medicamentos.operaciones.dispensacion;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.utils.BaseEntity;

@Entity
@Table(name = "dispensaciones")
public class Dispensacion extends BaseEntity {

    private String recetaNumero;

    private String codigoQR;

    private String dniPaciente;

    private String farmaciaCuit;

    private LocalDateTime fechaDispensacion;

    @OneToOne
    @JoinColumn(name = "unidad_trazable_id", unique = true, nullable = true)
    private UnidadTrazable unidadTrazable;

    public Dispensacion() {
    }

    public Dispensacion(String recetaNumero, String codigoQR, String dniPaciente, String farmaciaCuit, LocalDateTime fechaDispensacion, UnidadTrazable unidadTrazable) {
        this.recetaNumero = recetaNumero;
        this.codigoQR = codigoQR;
        this.dniPaciente = dniPaciente;
        this.farmaciaCuit = farmaciaCuit;
        this.fechaDispensacion = fechaDispensacion;
        this.unidadTrazable = unidadTrazable;
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

    public UnidadTrazable getUnidadTrazable() {
        return unidadTrazable;
    }

    public void setUnidadTrazable(UnidadTrazable unidadTrazable) {
        this.unidadTrazable = unidadTrazable;
    }
}
