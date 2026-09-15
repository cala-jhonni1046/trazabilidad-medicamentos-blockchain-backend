package ies.belgrano.medicamentos.telemetriatemperatura;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetrias_temperatura")
public class TelemetriaTemperatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sensorId;
    private Double temperatura;
    private LocalDateTime fechaHora;
    private Boolean alertaExcursion;
    private Long despachoLogisticoId;
    private String observaciones;

    public TelemetriaTemperatura() {
    }

    public TelemetriaTemperatura(Long id, String sensorId, Double temperatura, LocalDateTime fechaHora,
                                Boolean alertaExcursion, Long despachoLogisticoId, String observaciones) {
        this.id = id;
        this.sensorId = sensorId;
        this.temperatura = temperatura;
        this.fechaHora = fechaHora;
        this.alertaExcursion = alertaExcursion;
        this.despachoLogisticoId = despachoLogisticoId;
        this.observaciones = observaciones;
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
}
