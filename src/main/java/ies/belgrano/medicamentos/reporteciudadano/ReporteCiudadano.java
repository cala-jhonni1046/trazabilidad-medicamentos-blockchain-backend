package ies.belgrano.medicamentos.reporteciudadano;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.utils.BaseEntity;

@Entity
@Table(name = "reportes_ciudadanos")
public class ReporteCiudadano extends BaseEntity {

    private String codigoQRAfectado;

    private String inspectorAsignado;

    private String motivoReporte;

    private LocalDateTime fechaHoraReporte;

    private String estadoAuditoria;

    @ManyToOne
    @JoinColumn(name = "unidad_trazable_id", nullable = true)
    private UnidadTrazable unidadTrazable;

    @ManyToOne
    @JoinColumn(name = "inspector_id", nullable = true)
    private InspectorANMAT inspector;

    public ReporteCiudadano() {
    }

    public ReporteCiudadano(String codigoQRAfectado, String inspectorAsignado, String motivoReporte, LocalDateTime fechaHoraReporte, String estadoAuditoria, UnidadTrazable unidadTrazable, InspectorANMAT inspector) {
        this.codigoQRAfectado = codigoQRAfectado;
        this.inspectorAsignado = inspectorAsignado;
        this.motivoReporte = motivoReporte;
        this.fechaHoraReporte = fechaHoraReporte;
        this.estadoAuditoria = estadoAuditoria;
        this.unidadTrazable = unidadTrazable;
        this.inspector = inspector;
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

    public UnidadTrazable getUnidadTrazable() {
        return unidadTrazable;
    }

    public void setUnidadTrazable(UnidadTrazable unidadTrazable) {
        this.unidadTrazable = unidadTrazable;
    }

    public InspectorANMAT getInspector() {
        return inspector;
    }

    public void setInspector(InspectorANMAT inspector) {
        this.inspector = inspector;
    }
}
