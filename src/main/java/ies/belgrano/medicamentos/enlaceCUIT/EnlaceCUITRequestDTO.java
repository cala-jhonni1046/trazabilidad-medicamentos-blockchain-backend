package ies.belgrano.medicamentos.enlaceCUIT;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EnlaceCUITRequestDTO {

    @NotNull(message = "El ID de laboratorio es obligatorio")
    private Long laboratorioId;

    @NotNull(message = "El ID de distribuidor es obligatorio")
    private Long distribuidorId;

    @NotNull(message = "El ID de farmacia es obligatorio")
    private Long farmaciaId;

    private Long inspectorAprobadorId;

    @Size(max = 80, message = "El número de disposición ANMAT no puede superar los 80 caracteres")
    private String numeroDisposicionANMAT;

    @Size(max = 500, message = "La URL del certificado no puede superar los 500 caracteres")
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
