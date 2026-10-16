package ies.belgrano.medicamentos.cuarentena;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuarentenas")
public class Cuarentena extends BaseEntity {
    @Column(name = "codigo_cuarentena", nullable = false, unique = true, length = 80)
    private String codigoCuarentena;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
    @Column(name = "motivo", nullable = false, length = 500)
    private String motivo;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 24)
    private EstadoCuarentena estado = EstadoCuarentena.ABIERTA;
    @ManyToOne(optional = false)
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;
    @ManyToOne
    @JoinColumn(name = "inspector_anmat_id")
    private InspectorANMAT inspectorResponsable;
    @Column(name = "resolucion_final", length = 500)
    private String resolucionFinal;
    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    public String getCodigoCuarentena() { return codigoCuarentena; }
    public void setCodigoCuarentena(String value) { this.codigoCuarentena = value; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime value) { this.fechaInicio = value; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime value) { this.fechaFin = value; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String value) { this.motivo = value; }
    public EstadoCuarentena getEstado() { return estado; }
    public void setEstado(EstadoCuarentena value) { this.estado = value; }
    public Lote getLote() { return lote; }
    public void setLote(Lote value) { this.lote = value; }
    public InspectorANMAT getInspectorResponsable() { return inspectorResponsable; }
    public void setInspectorResponsable(InspectorANMAT value) { this.inspectorResponsable = value; }
    public String getResolucionFinal() { return resolucionFinal; }
    public void setResolucionFinal(String value) { this.resolucionFinal = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
}
