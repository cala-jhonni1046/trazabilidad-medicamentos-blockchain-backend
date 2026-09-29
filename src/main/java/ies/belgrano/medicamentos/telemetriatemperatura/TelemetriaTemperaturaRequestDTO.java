package ies.belgrano.medicamentos.telemetriatemperatura;

import java.time.LocalDateTime;

public class TelemetriaTemperaturaRequestDTO {

    private String sensorId;
    private Double temperatura;
    private LocalDateTime fechaHora;
    private Boolean alertaExcursion;
    private Long despachoLogisticoId;
    private String observaciones;

    public TelemetriaTemperaturaRequestDTO() {
    }

    public TelemetriaTemperaturaRequestDTO(String sensorId, Double temperatura, LocalDateTime fechaHora,
                                           Boolean alertaExcursion, Long despachoLogisticoId, String observaciones) {
        this.sensorId = sensorId;
        this.temperatura = temperatura;
        this.fechaHora = fechaHora;
        this.alertaExcursion = alertaExcursion;
        this.despachoLogisticoId = despachoLogisticoId;
        this.observaciones = observaciones;
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
}
