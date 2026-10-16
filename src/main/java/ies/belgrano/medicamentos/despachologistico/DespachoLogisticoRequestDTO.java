package ies.belgrano.medicamentos.despachologistico;

import java.time.LocalDateTime;

public class DespachoLogisticoRequestDTO {
    private String numeroRemito;
    private String codigoSeguimiento;
    private String origen;
    private String destino;
    private Long distribuidorId;
    private String vehiculoPatente;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaEstimadaEntrega;
    private LocalDateTime fechaEntregaReal;
    private EstadoDespacho estado;
    private Double temperaturaMinimaPermitida;
    private Double temperaturaMaximaPermitida;
    private String observaciones;
    private Long loteId;
    private Long enlaceCuitId;
    public String getNumeroRemito() { return numeroRemito; }
    public void setNumeroRemito(String value) { this.numeroRemito = value; }
    public String getCodigoSeguimiento() { return codigoSeguimiento; }
    public void setCodigoSeguimiento(String value) { this.codigoSeguimiento = value; }
    public String getOrigen() { return origen; }
    public void setOrigen(String value) { this.origen = value; }
    public String getDestino() { return destino; }
    public void setDestino(String value) { this.destino = value; }
    public Long getDistribuidorId() { return distribuidorId; }
    public void setDistribuidorId(Long value) { this.distribuidorId = value; }
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
    public Long getLoteId() { return loteId; }
    public void setLoteId(Long value) { this.loteId = value; }
    public Long getEnlaceCuitId() { return enlaceCuitId; }
    public void setEnlaceCuitId(Long value) { this.enlaceCuitId = value; }
}
