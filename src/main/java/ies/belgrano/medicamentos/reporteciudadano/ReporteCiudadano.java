package ies.belgrano.medicamentos.reporteciudadano;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.utils.BaseEntity;

@Entity
@Table(name = "reportes_ciudadanos")
public class ReporteCiudadano extends BaseEntity {

    @Column(name = "motivo_reporte", nullable = false, length = 1000)
    private String motivoReporte;
    @Column(name = "fecha_hora_reporte_utc", nullable = false)
    private LocalDateTime fechaHoraReporte;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 24)
    private EstadoReporte estado = EstadoReporte.PENDIENTE;

    @ManyToOne
    @JoinColumn(name = "unidad_trazable_id")
    private UnidadTrazable unidadTrazable;

    @ManyToOne
    @JoinColumn(name = "inspector_anmat_id")
    private InspectorANMAT inspector;

    public ReporteCiudadano() {
    }

    public ReporteCiudadano(String motivoReporte, LocalDateTime fechaHoraReporte, EstadoReporte estado, UnidadTrazable unidadTrazable, InspectorANMAT inspector) {
        this.motivoReporte = motivoReporte;
        this.fechaHoraReporte = fechaHoraReporte;
        this.estado = estado;
        this.unidadTrazable = unidadTrazable;
        this.inspector = inspector;
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

    public EstadoReporte getEstado() {
        return estado;
    }

    public void setEstado(EstadoReporte estado) {
        this.estado = estado;
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
