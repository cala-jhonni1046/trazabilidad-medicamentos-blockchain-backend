package ies.belgrano.medicamentos.telemetriagps;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetrias_gps")
public class TelemetriaGPS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dispositivoGpsId;
    private Double latitud;
    private Double longitud;
    private Double altitud;
    private Double velocidad;
    private LocalDateTime fechaHora;
    private Long despachoLogisticoId;
    private String direccionAproximada;

    public TelemetriaGPS() {
    }

    public TelemetriaGPS(Long id, String dispositivoGpsId, Double latitud, Double longitud, Double altitud,
                         Double velocidad, LocalDateTime fechaHora, Long despachoLogisticoId,
                         String direccionAproximada) {
        this.id = id;
        this.dispositivoGpsId = dispositivoGpsId;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.velocidad = velocidad;
        this.fechaHora = fechaHora;
        this.despachoLogisticoId = despachoLogisticoId;
        this.direccionAproximada = direccionAproximada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
