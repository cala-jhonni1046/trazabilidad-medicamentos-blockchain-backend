package ies.belgrano.medicamentos.telemetriatemperatura;

import java.time.LocalDateTime;

public class TelemetriaTemperaturaResponseDTO {

    private Long id;
    private String sensorId;
    private Double temperatura;
    private LocalDateTime fechaHora;
    private Boolean alertaExcursion;
    private Long despachoLogisticoId;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public TelemetriaTemperaturaResponseDTO() {
    }

    public TelemetriaTemperaturaResponseDTO(Long id, String sensorId, Double temperatura, LocalDateTime fechaHora,
                                            Boolean alertaExcursion, Long despachoLogisticoId, String observaciones,
                                            LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.sensorId = sensorId;
        this.temperatura = temperatura;
        this.fechaHora = fechaHora;
        this.alertaExcursion = alertaExcursion;
        this.despachoLogisticoId = despachoLogisticoId;
        this.observaciones = observaciones;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Boolean getAlertaExcursion() {
        return alertaExcursion;
    }

    public void setAlertaExcursion(Boolean alertaExcursion) {
        this.alertaExcursion = alertaExcursion;
    }

    public Long getDespachoLogisticoId() {
        return despachoLogisticoId;
    }

    public void setDespachoLogisticoId(Long despachoLogisticoId) {
        this.despachoLogisticoId = despachoLogisticoId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
