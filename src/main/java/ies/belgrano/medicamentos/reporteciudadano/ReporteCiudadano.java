package ies.belgrano.medicamentos.reporteciudadano;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reportes_ciudadanos")
public class ReporteCiudadano {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String codigoQRAfectado;

    private String inspectorAsignado;

    private String motivoReporte;

    private LocalDateTime fechaHoraReporte;

    private String estadoAuditoria;

    public ReporteCiudadano() {
    }

    public ReporteCiudadano(UUID id, String codigoQRAfectado, String inspectorAsignado, String motivoReporte, LocalDateTime fechaHoraReporte, String estadoAuditoria) {
        this.id = id;
        this.codigoQRAfectado = codigoQRAfectado;
        this.inspectorAsignado = inspectorAsignado;
        this.motivoReporte = motivoReporte;
        this.fechaHoraReporte = fechaHoraReporte;
        this.estadoAuditoria = estadoAuditoria;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
}
