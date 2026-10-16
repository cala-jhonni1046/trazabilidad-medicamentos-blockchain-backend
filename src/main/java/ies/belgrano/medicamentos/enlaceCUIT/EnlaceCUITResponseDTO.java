package ies.belgrano.medicamentos.enlaceCUIT;

import java.time.LocalDateTime;

public class EnlaceCUITResponseDTO {
    private Long id;
    private Long laboratorioId;
    private String laboratorioNombre;
    private String laboratorioCuit;
    private Long distribuidorId;
    private String distribuidorNombre;
    private String distribuidorCuit;
    private Long farmaciaId;
    private String farmaciaNombre;
    private String farmaciaCuit;
    private Long inspectorAprobadorId;
    private String inspectorAprobadorNombre;
    private String numeroDisposicionANMAT;
    private String documentoCertificadoUrl;
    private EstadoEnlaceCuit estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaAprobacionANMAT;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }
    public Long getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(Long value) { this.laboratorioId = value; }
    public String getLaboratorioNombre() { return laboratorioNombre; }
    public void setLaboratorioNombre(String value) { this.laboratorioNombre = value; }
    public String getLaboratorioCuit() { return laboratorioCuit; }
    public void setLaboratorioCuit(String value) { this.laboratorioCuit = value; }
    public Long getDistribuidorId() { return distribuidorId; }
    public void setDistribuidorId(Long value) { this.distribuidorId = value; }
    public String getDistribuidorNombre() { return distribuidorNombre; }
    public void setDistribuidorNombre(String value) { this.distribuidorNombre = value; }
    public String getDistribuidorCuit() { return distribuidorCuit; }
    public void setDistribuidorCuit(String value) { this.distribuidorCuit = value; }
    public Long getFarmaciaId() { return farmaciaId; }
    public void setFarmaciaId(Long value) { this.farmaciaId = value; }
    public String getFarmaciaNombre() { return farmaciaNombre; }
    public void setFarmaciaNombre(String value) { this.farmaciaNombre = value; }
    public String getFarmaciaCuit() { return farmaciaCuit; }
    public void setFarmaciaCuit(String value) { this.farmaciaCuit = value; }
    public Long getInspectorAprobadorId() { return inspectorAprobadorId; }
    public void setInspectorAprobadorId(Long value) { this.inspectorAprobadorId = value; }
    public String getInspectorAprobadorNombre() { return inspectorAprobadorNombre; }
    public void setInspectorAprobadorNombre(String value) { this.inspectorAprobadorNombre = value; }
    public String getNumeroDisposicionANMAT() { return numeroDisposicionANMAT; }
    public void setNumeroDisposicionANMAT(String value) { this.numeroDisposicionANMAT = value; }
    public String getDocumentoCertificadoUrl() { return documentoCertificadoUrl; }
    public void setDocumentoCertificadoUrl(String value) { this.documentoCertificadoUrl = value; }
    public EstadoEnlaceCuit getEstado() { return estado; }
    public void setEstado(EstadoEnlaceCuit value) { this.estado = value; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime value) { this.fechaSolicitud = value; }
    public LocalDateTime getFechaAprobacionANMAT() { return fechaAprobacionANMAT; }
    public void setFechaAprobacionANMAT(LocalDateTime value) { this.fechaAprobacionANMAT = value; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime value) { this.fechaCreacion = value; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime value) { this.fechaActualizacion = value; }
}
