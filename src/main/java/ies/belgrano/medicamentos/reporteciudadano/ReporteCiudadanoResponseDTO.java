package ies.belgrano.medicamentos.reporteciudadano;

import java.time.LocalDateTime;

public class ReporteCiudadanoResponseDTO {

    private Long id;
    private String codigoQRAfectado;
    private String inspectorAsignado;
    private String motivoReporte;
    private LocalDateTime fechaHoraReporte;
    private String estadoAuditoria;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public ReporteCiudadanoResponseDTO() {
    }

    public ReporteCiudadanoResponseDTO(Long id, String codigoQRAfectado, String inspectorAsignado, String motivoReporte, LocalDateTime fechaHoraReporte, String estadoAuditoria, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.codigoQRAfectado = codigoQRAfectado;
        this.inspectorAsignado = inspectorAsignado;
        this.motivoReporte = motivoReporte;
        this.fechaHoraReporte = fechaHoraReporte;
        this.estadoAuditoria = estadoAuditoria;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoQRAfectado() {
        return codigoQRAfectado;
    }

    public void setCodigoQRAfectado(String codigoQRAfectado) {
        this.codigoQRAfectado = codigoQRAfectado;
    }

    public String getInspectorAsignado() {
        return inspectorAsignado;
    }

    public void setInspectorAsignado(String inspectorAsignado) {
        this.inspectorAsignado = inspectorAsignado;
    }

    public String getMotivoReporte() {
        return motivoReporte;
    }

    public void setMotivoReporte(String motivoReporte) {
        this.motivoReporte = motivoReporte;
    }

    public LocalDateTime getFechaHoraReporte() {
        return fechaHoraReporte;
    }

    public void setFechaHoraReporte(LocalDateTime fechaHoraReporte) {
        this.fechaHoraReporte = fechaHoraReporte;
    }

    public String getEstadoAuditoria() {
        return estadoAuditoria;
    }

    public void setEstadoAuditoria(String estadoAuditoria) {
        this.estadoAuditoria = estadoAuditoria;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
