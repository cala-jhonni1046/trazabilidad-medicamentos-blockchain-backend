package ies.belgrano.medicamentos.telemetriatemperatura;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetrias_temperatura")
public class TelemetriaTemperatura extends BaseEntity {

    @Column(name = "sensor_id", nullable = false, length = 120)
    private String sensorId;
    @Column(name = "temperatura_celsius", nullable = false)
    private Double temperatura;
    @Column(name = "fecha_hora_utc", nullable = false)
    private LocalDateTime fechaHora;
    @Column(name = "alerta_excursion")
    private Boolean alertaExcursion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "despacho_logistico_id", nullable = false)
    private DespachoLogistico despachoLogistico;

    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    public TelemetriaTemperatura() {
    }

    public TelemetriaTemperatura(String sensorId, Double temperatura, LocalDateTime fechaHora,
                                Boolean alertaExcursion, DespachoLogistico despachoLogistico, String observaciones) {
        this.sensorId = sensorId;
        this.temperatura = temperatura;
        this.fechaHora = fechaHora;
        this.alertaExcursion = alertaExcursion;
        this.despachoLogistico = despachoLogistico;
        this.observaciones = observaciones;
    }

    public TelemetriaTemperatura(Long id, String sensorId, Double temperatura, LocalDateTime fechaHora,
                                Boolean alertaExcursion, DespachoLogistico despachoLogistico, String observaciones) {
        this(sensorId, temperatura, fechaHora, alertaExcursion, despachoLogistico, observaciones);
        this.setId(id);
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

    public DespachoLogistico getDespachoLogistico() {
        return despachoLogistico;
    }

    public void setDespachoLogistico(DespachoLogistico despachoLogistico) {
        this.despachoLogistico = despachoLogistico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
