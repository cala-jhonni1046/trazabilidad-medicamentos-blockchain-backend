package ies.belgrano.medicamentos.reporteciudadano;

import java.time.LocalDateTime;

public class ReporteCiudadanoRequestDTO {
    private String motivoReporte;
    private LocalDateTime fechaHoraReporte;
    private Long unidadTrazableId;
    private Long inspectorAsignadoId;
    private EstadoReporte estado;
    public String getMotivoReporte() { return motivoReporte; }
    public void setMotivoReporte(String value) { this.motivoReporte = value; }
    public LocalDateTime getFechaHoraReporte() { return fechaHoraReporte; }
    public void setFechaHoraReporte(LocalDateTime value) { this.fechaHoraReporte = value; }
    public Long getUnidadTrazableId() { return unidadTrazableId; }
    public void setUnidadTrazableId(Long value) { this.unidadTrazableId = value; }
    public Long getInspectorAsignadoId() { return inspectorAsignadoId; }
    public void setInspectorAsignadoId(Long value) { this.inspectorAsignadoId = value; }
    public EstadoReporte getEstado() { return estado; }
    public void setEstado(EstadoReporte value) { this.estado = value; }
}
