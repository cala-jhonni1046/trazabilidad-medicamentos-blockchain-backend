package ies.belgrano.medicamentos.despachologistico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.enlaceCUIT.EnlaceCUIT;
import ies.belgrano.medicamentos.organizacion.Organizacion;
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
@Table(name = "despachos_logisticos")
public class DespachoLogistico extends BaseEntity {
    @Column(name = "numero_remito", nullable = false, unique = true, length = 80)
    private String numeroRemito;
    @Column(name = "codigo_seguimiento", unique = true, length = 100)
    private String codigoSeguimiento;
    @Column(name = "origen", length = 240)
    private String origen;
    @Column(name = "destino", length = 240)
    private String destino;
    @ManyToOne(optional = false)
    @JoinColumn(name = "distribuidor_id", nullable = false)
    private Organizacion distribuidor;
    @Column(name = "vehiculo_patente", length = 16)
    private String vehiculoPatente;
    @Column(name = "fecha_salida_utc")
    private LocalDateTime fechaSalida;
    @Column(name = "fecha_estimada_entrega_utc")
    private LocalDateTime fechaEstimadaEntrega;
    @Column(name = "fecha_entrega_real_utc")
    private LocalDateTime fechaEntregaReal;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 24)
    private EstadoDespacho estado = EstadoDespacho.PREPARADO;
    @Column(name = "temperatura_minima_celsius")
    private Double temperaturaMinimaPermitida;
    @Column(name = "temperatura_maxima_celsius")
    private Double temperaturaMaximaPermitida;
    @Column(name = "observaciones", length = 1000)
    private String observaciones;
    @ManyToOne(optional = false)
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;
    @ManyToOne(optional = false)
    @JoinColumn(name = "enlace_cuit_id", nullable = false)
    private EnlaceCUIT enlaceCuit;
    @OneToMany(mappedBy = "despacho")
    private List<EventoCustodia> eventos = new ArrayList<>();

    public String getNumeroRemito() { return numeroRemito; }
    public void setNumeroRemito(String value) { this.numeroRemito = value; }
    public String getCodigoSeguimiento() { return codigoSeguimiento; }
    public void setCodigoSeguimiento(String value) { this.codigoSeguimiento = value; }
    public String getOrigen() { return origen; }
    public void setOrigen(String value) { this.origen = value; }
    public String getDestino() { return destino; }
    public void setDestino(String value) { this.destino = value; }
    public Organizacion getDistribuidor() { return distribuidor; }
    public void setDistribuidor(Organizacion value) { this.distribuidor = value; }
    public String getVehiculoPatente() { return vehiculoPatente; }
    public void setVehiculoPatente(String value) { this.vehiculoPatente = value; }
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime value) { this.fechaSalida = value; }
    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDateTime value) { this.fechaEstimadaEntrega = value; }
    public LocalDateTime getFechaEntregaReal() { return fechaEntregaReal; }
    public void setFechaEntregaReal(LocalDateTime value) { this.fechaEntregaReal = value; }
    public EstadoDespacho getEstado() { return estado; }
    public void setEstado(EstadoDespacho value) { this.estado = value; }
    public Double getTemperaturaMinimaPermitida() { return temperaturaMinimaPermitida; }
    public void setTemperaturaMinimaPermitida(Double value) { this.temperaturaMinimaPermitida = value; }
    public Double getTemperaturaMaximaPermitida() { return temperaturaMaximaPermitida; }
    public void setTemperaturaMaximaPermitida(Double value) { this.temperaturaMaximaPermitida = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
    public Lote getLote() { return lote; }
    public void setLote(Lote value) { this.lote = value; }
    public EnlaceCUIT getEnlaceCuit() { return enlaceCuit; }
    public void setEnlaceCuit(EnlaceCUIT value) { this.enlaceCuit = value; }
    public List<EventoCustodia> getEventos() { return eventos; }
    public void setEventos(List<EventoCustodia> value) { this.eventos = value; }
}
