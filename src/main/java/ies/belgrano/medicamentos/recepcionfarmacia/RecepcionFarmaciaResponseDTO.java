package ies.belgrano.medicamentos.recepcionfarmacia;

import java.time.LocalDateTime;

public class RecepcionFarmaciaResponseDTO {
    private Long id;
    private String numeroComprobante;
    private LocalDateTime fechaHoraRecepcion;
    private Long farmaciaId;
    private String farmaciaCuit;
    private String farmaciaNombre;
    private String farmaceuticoResponsable;
    private Long despachoLogisticoId;
    private String numeroRemito;
    private EstadoRecepcion estadoConformidad;
    private Boolean cadenaFrioIntacta;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }
    public String getNumeroComprobante() { return numeroComprobante; }
    public void setNumeroComprobante(String value) { this.numeroComprobante = value; }
    public LocalDateTime getFechaHoraRecepcion() { return fechaHoraRecepcion; }
    public void setFechaHoraRecepcion(LocalDateTime value) { this.fechaHoraRecepcion = value; }
    public Long getFarmaciaId() { return farmaciaId; }
    public void setFarmaciaId(Long value) { this.farmaciaId = value; }
    public String getFarmaciaCuit() { return farmaciaCuit; }
    public void setFarmaciaCuit(String value) { this.farmaciaCuit = value; }
    public String getFarmaciaNombre() { return farmaciaNombre; }
    public void setFarmaciaNombre(String value) { this.farmaciaNombre = value; }
    public String getFarmaceuticoResponsable() { return farmaceuticoResponsable; }
    public void setFarmaceuticoResponsable(String value) { this.farmaceuticoResponsable = value; }
    public Long getDespachoLogisticoId() { return despachoLogisticoId; }
    public void setDespachoLogisticoId(Long value) { this.despachoLogisticoId = value; }
    public String getNumeroRemito() { return numeroRemito; }
    public void setNumeroRemito(String value) { this.numeroRemito = value; }
    public EstadoRecepcion getEstadoConformidad() { return estadoConformidad; }
    public void setEstadoConformidad(EstadoRecepcion value) { this.estadoConformidad = value; }
    public Boolean getCadenaFrioIntacta() { return cadenaFrioIntacta; }
    public void setCadenaFrioIntacta(Boolean value) { this.cadenaFrioIntacta = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime value) { this.fechaCreacion = value; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime value) { this.fechaActualizacion = value; }
}
