package ies.belgrano.medicamentos.enlaceCUIT;

import java.time.LocalDateTime;

public class EnlaceCUITRequestDTO {
    private Long laboratorioId;
    private Long distribuidorId;
    private Long farmaciaId;
    private Long inspectorAprobadorId;
    private String numeroDisposicionANMAT;
    private String documentoCertificadoUrl;
    private EstadoEnlaceCuit estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaAprobacionANMAT;
    public Long getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(Long value) { this.laboratorioId = value; }
    public Long getDistribuidorId() { return distribuidorId; }
    public void setDistribuidorId(Long value) { this.distribuidorId = value; }
    public Long getFarmaciaId() { return farmaciaId; }
    public void setFarmaciaId(Long value) { this.farmaciaId = value; }
    public Long getInspectorAprobadorId() { return inspectorAprobadorId; }
    public void setInspectorAprobadorId(Long value) { this.inspectorAprobadorId = value; }
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
}
