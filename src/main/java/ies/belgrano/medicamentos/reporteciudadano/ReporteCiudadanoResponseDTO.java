package ies.belgrano.medicamentos.reporteciudadano;

import java.time.LocalDateTime;

public class ReporteCiudadanoResponseDTO {
    private Long id;
    private String motivoReporte;
    private LocalDateTime fechaHoraReporte;
    private EstadoReporte estado;
    private Long unidadTrazableId;
    private String codigoUnicoQR;
    private Long inspectorAsignadoId;
    private String inspectorNombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }
    public String getMotivoReporte() { return motivoReporte; }
    public void setMotivoReporte(String value) { this.motivoReporte = value; }
    public LocalDateTime getFechaHoraReporte() { return fechaHoraReporte; }
    public void setFechaHoraReporte(LocalDateTime value) { this.fechaHoraReporte = value; }
    public EstadoReporte getEstado() { return estado; }
    public void setEstado(EstadoReporte value) { this.estado = value; }
    public Long getUnidadTrazableId() { return unidadTrazableId; }
    public void setUnidadTrazableId(Long value) { this.unidadTrazableId = value; }
    public String getCodigoUnicoQR() { return codigoUnicoQR; }
    public void setCodigoUnicoQR(String value) { this.codigoUnicoQR = value; }
    public Long getInspectorAsignadoId() { return inspectorAsignadoId; }
    public void setInspectorAsignadoId(Long value) { this.inspectorAsignadoId = value; }
    public String getInspectorNombre() { return inspectorNombre; }
    public void setInspectorNombre(String value) { this.inspectorNombre = value; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime value) { this.fechaCreacion = value; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime value) { this.fechaActualizacion = value; }
}
