package ies.belgrano.medicamentos.despachologistico;

import java.time.LocalDateTime;

public class DespachoLogisticoResponseDTO {
    private Long id;
    private String numeroRemito;
    private String codigoSeguimiento;
    private String origen;
    private String destino;
    private Long distribuidorId;
    private String distribuidorNombre;
    private String distribuidorCuit;
    private Long laboratorioId;
    private String laboratorioNombre;
    private String laboratorioCuit;
    private Long farmaciaDestinoId;
    private String farmaciaDestinoNombre;
    private String farmaciaDestinoCuit;
    private String vehiculoPatente;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaEstimadaEntrega;
    private LocalDateTime fechaEntregaReal;
    private EstadoDespacho estado;
    private Double temperaturaMinimaPermitida;
    private Double temperaturaMaximaPermitida;
    private String observaciones;
    private Long loteId;
    private String codigoLote;
    private String medicamentoNombre;
    private Long enlaceCuitId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public DespachoLogisticoResponseDTO() {
    }

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

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

    public String getDistribuidorNombre() { return distribuidorNombre; }
    public void setDistribuidorNombre(String value) { this.distribuidorNombre = value; }

    public String getDistribuidorCuit() { return distribuidorCuit; }
    public void setDistribuidorCuit(String value) { this.distribuidorCuit = value; }

    public Long getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(Long value) { this.laboratorioId = value; }

    public String getLaboratorioNombre() { return laboratorioNombre; }
    public void setLaboratorioNombre(String value) { this.laboratorioNombre = value; }

    public String getLaboratorioCuit() { return laboratorioCuit; }
    public void setLaboratorioCuit(String value) { this.laboratorioCuit = value; }

    public Long getFarmaciaDestinoId() { return farmaciaDestinoId; }
    public void setFarmaciaDestinoId(Long value) { this.farmaciaDestinoId = value; }

    public String getFarmaciaDestinoNombre() { return farmaciaDestinoNombre; }
    public void setFarmaciaDestinoNombre(String value) { this.farmaciaDestinoNombre = value; }

    public String getFarmaciaDestinoCuit() { return farmaciaDestinoCuit; }
    public void setFarmaciaDestinoCuit(String value) { this.farmaciaDestinoCuit = value; }

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

    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String value) { this.codigoLote = value; }

    public String getMedicamentoNombre() { return medicamentoNombre; }
    public void setMedicamentoNombre(String value) { this.medicamentoNombre = value; }

    public Long getEnlaceCuitId() { return enlaceCuitId; }
    public void setEnlaceCuitId(Long value) { this.enlaceCuitId = value; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime value) { this.fechaCreacion = value; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime value) { this.fechaActualizacion = value; }
}
