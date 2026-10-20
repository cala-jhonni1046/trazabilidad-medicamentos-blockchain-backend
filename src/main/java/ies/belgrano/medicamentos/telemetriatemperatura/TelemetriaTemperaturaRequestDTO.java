package ies.belgrano.medicamentos.telemetriatemperatura;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TelemetriaTemperaturaRequestDTO {

    @NotBlank(message = "El identificador del sensor es obligatorio")
    @Size(max = 120, message = "El identificador del sensor no puede superar los 120 caracteres")
    private String sensorId;

    @NotNull(message = "La temperatura es obligatoria")
    private Double temperatura;

    @NotNull(message = "La fecha y hora de la lectura es obligatoria")
    private LocalDateTime fechaHora;

    /**
     * Campo informativo. La alerta de excursión es determinada estrictamente por el servidor
     * comparando el valor térmico contra los límites del lote. Si el cliente envía este campo,
     * el backend lo ignora y sobreescribe con el cálculo oficial.
     */
    private Boolean alertaExcursion;

    @NotNull(message = "El ID de despacho logístico es obligatorio")
    @JsonAlias({"despachoId"})
    private Long despachoLogisticoId;

    @Size(max = 1000, message = "Las observaciones no pueden superar los 1000 caracteres")
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

    public void setDespachoId(Long despachoId) {
        if (this.despachoLogisticoId == null) {
            this.despachoLogisticoId = despachoId;
        }
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

