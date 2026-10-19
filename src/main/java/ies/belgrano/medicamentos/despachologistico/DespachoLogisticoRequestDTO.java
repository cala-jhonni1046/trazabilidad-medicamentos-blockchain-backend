package ies.belgrano.medicamentos.despachologistico;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DespachoLogisticoRequestDTO {

    @NotBlank(message = "El número de remito es obligatorio.")
    @Size(max = 80, message = "El número de remito no puede superar los 80 caracteres.")
    private String numeroRemito;

    @Size(max = 100, message = "El código de seguimiento no puede superar los 100 caracteres.")
    private String codigoSeguimiento;

    @Size(max = 240, message = "El origen no puede superar los 240 caracteres.")
    private String origen;

    @Size(max = 240, message = "El destino no puede superar los 240 caracteres.")
    private String destino;

    @NotNull(message = "El ID de distribuidor es obligatorio.")
    private Long distribuidorId;

    private Long farmaciaDestinoId;

    @Size(max = 16, message = "La patente del vehículo no puede superar los 16 caracteres.")
    private String vehiculoPatente;

    private LocalDateTime fechaSalida;
    private LocalDateTime fechaEstimadaEntrega;
    private LocalDateTime fechaEntregaReal;
    private EstadoDespacho estado;
    private Double temperaturaMinimaPermitida;
    private Double temperaturaMaximaPermitida;

    @Size(max = 1000, message = "Las observaciones no pueden superar los 1000 caracteres.")
    private String observaciones;

    @NotNull(message = "El ID de lote es obligatorio.")
    private Long loteId;

    @NotNull(message = "El ID de enlace CUIT es obligatorio.")
    private Long enlaceCuitId;

    public DespachoLogisticoRequestDTO() {
    }

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

    public Long getFarmaciaDestinoId() { return farmaciaDestinoId; }
    public void setFarmaciaDestinoId(Long value) { this.farmaciaDestinoId = value; }

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
