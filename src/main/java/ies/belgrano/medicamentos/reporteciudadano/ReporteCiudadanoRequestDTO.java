package ies.belgrano.medicamentos.reporteciudadano;

import java.time.LocalDateTime;

public class ReporteCiudadanoRequestDTO {

    private String codigoQRAfectado;
    private String inspectorAsignado;
    private String motivoReporte;
    private LocalDateTime fechaHoraReporte;
    private String estadoAuditoria;

    public ReporteCiudadanoRequestDTO() {
    }

    public ReporteCiudadanoRequestDTO(String codigoQRAfectado, String inspectorAsignado, String motivoReporte, LocalDateTime fechaHoraReporte, String estadoAuditoria) {
        this.codigoQRAfectado = codigoQRAfectado;
        this.inspectorAsignado = inspectorAsignado;
        this.motivoReporte = motivoReporte;
        this.fechaHoraReporte = fechaHoraReporte;
        this.estadoAuditoria = estadoAuditoria;
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
}
