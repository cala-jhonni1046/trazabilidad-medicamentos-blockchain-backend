package ies.belgrano.medicamentos.lote;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.medicamento.Medicamento;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "lotes")
public class Lote extends BaseEntity {
    @Column(name = "codigo_lote", nullable = false, unique = true, length = 80)
    private String codigoLote;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "laboratorio_id", nullable = false)
    private Organizacion laboratorio;

    @Column(name = "temperatura_minima_celsius")
    private Double tempMin;

    @Column(name = "temperatura_maxima_celsius")
    private Double tempMax;

    @Column(name = "fecha_fabricacion")
    private LocalDateTime fechaHoraFabricacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 24)
    private EstadoLote estado = EstadoLote.CREADO;

    @OneToMany(mappedBy = "lote")
    private List<UnidadTrazable> unidades = new ArrayList<>();

    @OneToMany(mappedBy = "lote")
    private List<DespachoLogistico> despachos = new ArrayList<>();

    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }
    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public Organizacion getLaboratorio() { return laboratorio; }
    public void setLaboratorio(Organizacion laboratorio) { this.laboratorio = laboratorio; }
    public Double getTempMin() { return tempMin; }
    public void setTempMin(Double tempMin) { this.tempMin = tempMin; }
    public Double getTempMax() { return tempMax; }
    public void setTempMax(Double tempMax) { this.tempMax = tempMax; }
    public LocalDateTime getFechaHoraFabricacion() { return fechaHoraFabricacion; }
    public void setFechaHoraFabricacion(LocalDateTime fechaHoraFabricacion) { this.fechaHoraFabricacion = fechaHoraFabricacion; }
    public EstadoLote getEstado() { return estado; }
    public void setEstado(EstadoLote estado) { this.estado = estado; }
    public List<UnidadTrazable> getUnidades() { return unidades; }
    public void setUnidades(List<UnidadTrazable> unidades) { this.unidades = unidades; }
    public List<DespachoLogistico> getDespachos() { return despachos; }
    public void setDespachos(List<DespachoLogistico> despachos) { this.despachos = despachos; }
}
