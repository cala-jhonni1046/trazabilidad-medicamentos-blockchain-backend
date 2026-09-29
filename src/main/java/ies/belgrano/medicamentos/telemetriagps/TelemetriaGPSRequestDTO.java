package ies.belgrano.medicamentos.telemetriagps;

import java.time.LocalDateTime;

public class TelemetriaGPSRequestDTO {

    private String dispositivoGpsId;
    private Double latitud;
    private Double longitud;
    private Double altitud;
    private Double velocidad;
    private LocalDateTime fechaHora;
    private Long despachoLogisticoId;
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

    public String getDireccionAproximada() {
        return direccionAproximada;
    }

    public void setDireccionAproximada(String direccionAproximada) {
        this.direccionAproximada = direccionAproximada;
    }
}
