package ies.belgrano.medicamentos.recepcionfarmacia;

import java.time.LocalDateTime;

public class RecepcionFarmaciaRequestDTO {

    private String numeroComprobante;
    private LocalDateTime fechaHoraRecepcion;
    private String farmaciaCuit;
    private String farmaciaNombre;
    private String farmaceuticoResponsable;
    private Long despachoLogisticoId;
    private String estadoConformidad;
    private Boolean cadenaFrioIntacta;
    private String observaciones;

    public RecepcionFarmaciaRequestDTO() {
    }

    public RecepcionFarmaciaRequestDTO(String numeroComprobante, LocalDateTime fechaHoraRecepcion,
                                      String farmaciaCuit, String farmaciaNombre, String farmaceuticoResponsable,
                                      Long despachoLogisticoId, String estadoConformidad, Boolean cadenaFrioIntacta,
                                      String observaciones) {
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
