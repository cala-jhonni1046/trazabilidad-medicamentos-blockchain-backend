package ies.belgrano.medicamentos.recepcionfarmacia;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "recepciones_farmacia")
public class RecepcionFarmacia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroComprobante;
    private LocalDateTime fechaHoraRecepcion;
    private String farmaciaCuit;
    private String farmaciaNombre;
    private String farmaceuticoResponsable;
    private Long despachoLogisticoId;
    private String estadoConformidad;
    private Boolean cadenaFrioIntacta;
    private String observaciones;

    public RecepcionFarmacia() {
    }

    public RecepcionFarmacia(Long id, String numeroComprobante, LocalDateTime fechaHoraRecepcion,
                            String farmaciaCuit, String farmaciaNombre, String farmaceuticoResponsable,
                            Long despachoLogisticoId, String estadoConformidad, Boolean cadenaFrioIntacta,
                            String observaciones) {
        this.id = id;
        this.numeroComprobante = numeroComprobante;
        this.fechaHoraRecepcion = fechaHoraRecepcion;
        this.farmaciaCuit = farmaciaCuit;
        this.farmaciaNombre = farmaciaNombre;
        this.farmaceuticoResponsable = farmaceuticoResponsable;
        this.despachoLogisticoId = despachoLogisticoId;
        this.estadoConformidad = estadoConformidad;
        this.cadenaFrioIntacta = cadenaFrioIntacta;
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public LocalDateTime getFechaHoraRecepcion() {
        return fechaHoraRecepcion;
    }

    public void setFechaHoraRecepcion(LocalDateTime fechaHoraRecepcion) {
        this.fechaHoraRecepcion = fechaHoraRecepcion;
    }

    public String getFarmaciaCuit() {
        return farmaciaCuit;
    }

    public void setFarmaciaCuit(String farmaciaCuit) {
        this.farmaciaCuit = farmaciaCuit;
    }

    public String getFarmaciaNombre() {
        return farmaciaNombre;
    }

    public void setFarmaciaNombre(String farmaciaNombre) {
        this.farmaciaNombre = farmaciaNombre;
    }

    public String getFarmaceuticoResponsable() {
        return farmaceuticoResponsable;
    }

    public void setFarmaceuticoResponsable(String farmaceuticoResponsable) {
        this.farmaceuticoResponsable = farmaceuticoResponsable;
    }

    public Long getDespachoLogisticoId() {
        return despachoLogisticoId;
    }

    public void setDespachoLogisticoId(Long despachoLogisticoId) {
        this.despachoLogisticoId = despachoLogisticoId;
    }

    public String getEstadoConformidad() {
        return estadoConformidad;
    }

    public void setEstadoConformidad(String estadoConformidad) {
        this.estadoConformidad = estadoConformidad;
    }

    public Boolean getCadenaFrioIntacta() {
        return cadenaFrioIntacta;
    }

    public void setCadenaFrioIntacta(Boolean cadenaFrioIntacta) {
        this.cadenaFrioIntacta = cadenaFrioIntacta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
