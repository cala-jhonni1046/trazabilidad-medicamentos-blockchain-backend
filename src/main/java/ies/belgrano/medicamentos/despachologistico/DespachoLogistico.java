package ies.belgrano.medicamentos.despachologistico;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "despachos_logisticos")
public class DespachoLogistico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroRemito;
    private String codigoSeguimiento;
    private String origen;
    private String destino;
    private String transportista;
    private String vehiculoPatente;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaEstimadaEntrega;
    private LocalDateTime fechaEntregaReal;
    private String estado;
    private Double temperaturaMinimaPermitida;
    private Double temperaturaMaximaPermitida;
    private String observaciones;

    public DespachoLogistico() {
    }

    public DespachoLogistico(Long id, String numeroRemito, String codigoSeguimiento, String origen, String destino,
                            String transportista, String vehiculoPatente, LocalDateTime fechaSalida,
                            LocalDateTime fechaEstimadaEntrega, LocalDateTime fechaEntregaReal, String estado,
                            Double temperaturaMinimaPermitida, Double temperaturaMaximaPermitida, String observaciones) {
        this.id = id;
        this.numeroRemito = numeroRemito;
        this.codigoSeguimiento = codigoSeguimiento;
        this.origen = origen;
        this.destino = destino;
        this.transportista = transportista;
        this.vehiculoPatente = vehiculoPatente;
        this.fechaSalida = fechaSalida;
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
        this.fechaEntregaReal = fechaEntregaReal;
        this.estado = estado;
        this.temperaturaMinimaPermitida = temperaturaMinimaPermitida;
        this.temperaturaMaximaPermitida = temperaturaMaximaPermitida;
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroRemito() {
        return numeroRemito;
    }

    public void setNumeroRemito(String numeroRemito) {
        this.numeroRemito = numeroRemito;
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public String getVehiculoPatente() {
        return vehiculoPatente;
    }

    public void setVehiculoPatente(String vehiculoPatente) {
        this.vehiculoPatente = vehiculoPatente;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalDateTime getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    public LocalDateTime getFechaEntregaReal() {
        return fechaEntregaReal;
    }

    public void setFechaEntregaReal(LocalDateTime fechaEntregaReal) {
        this.fechaEntregaReal = fechaEntregaReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTemperaturaMinimaPermitida() {
        return temperaturaMinimaPermitida;
    }

    public void setTemperaturaMinimaPermitida(Double temperaturaMinimaPermitida) {
        this.temperaturaMinimaPermitida = temperaturaMinimaPermitida;
    }

    public Double getTemperaturaMaximaPermitida() {
        return temperaturaMaximaPermitida;
    }

    public void setTemperaturaMaximaPermitida(Double temperaturaMaximaPermitida) {
        this.temperaturaMaximaPermitida = temperaturaMaximaPermitida;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
