package ies.belgrano.medicamentos.telemetriagps;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TelemetriaGPSRequestDTO {

    @Size(max = 120, message = "El identificador del dispositivo GPS no puede superar los 120 caracteres")
    private String dispositivoGpsId;

    @DecimalMin(value = "-90.0", message = "La latitud debe estar comprendida entre -90 y 90 grados")
    @DecimalMax(value = "90.0", message = "La latitud debe estar comprendida entre -90 y 90 grados")
    private Double latitud;

    @DecimalMin(value = "-180.0", message = "La longitud debe estar comprendida entre -180 y 180 grados")
    @DecimalMax(value = "180.0", message = "La longitud debe estar comprendida entre -180 y 180 grados")
    private Double longitud;

    private Double altitud;

    private Double velocidad;

    @NotNull(message = "La fecha y hora de la lectura es obligatoria")
    private LocalDateTime fechaHora;

    @NotNull(message = "El ID de despacho logístico es obligatorio")
    @JsonAlias({"despachoId"})
    private Long despachoLogisticoId;

    @Size(max = 240, message = "La dirección aproximada no puede superar los 240 caracteres")
    private String direccionAproximada;

    public TelemetriaGPSRequestDTO() {
    }

    public TelemetriaGPSRequestDTO(String dispositivoGpsId, Double latitud, Double longitud, Double altitud,
                                   Double velocidad, LocalDateTime fechaHora, Long despachoLogisticoId,
                                   String direccionAproximada) {
        this.dispositivoGpsId = dispositivoGpsId;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.velocidad = velocidad;
        this.fechaHora = fechaHora;
        this.despachoLogisticoId = despachoLogisticoId;
        this.direccionAproximada = direccionAproximada;
    }

    public String getDispositivoGpsId() {
        return dispositivoGpsId;
    }

    public void setDispositivoGpsId(String dispositivoGpsId) {
        this.dispositivoGpsId = dispositivoGpsId;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getAltitud() {
        return altitud;
    }

    public void setAltitud(Double altitud) {
        this.altitud = altitud;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
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

    public String getDireccionAproximada() {
        return direccionAproximada;
    }

    public void setDireccionAproximada(String direccionAproximada) {
        this.direccionAproximada = direccionAproximada;
    }
}

