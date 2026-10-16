package ies.belgrano.medicamentos.telemetriagps;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetrias_gps")
public class TelemetriaGPS extends BaseEntity {

    @Column(name = "dispositivo_gps_id", length = 120)
    private String dispositivoGpsId;
    @Column(name = "latitud")
    private Double latitud;
    @Column(name = "longitud")
    private Double longitud;
    @Column(name = "altitud_metros")
    private Double altitud;
    @Column(name = "velocidad")
    private Double velocidad;
    @Column(name = "fecha_hora_utc", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "despacho_logistico_id")
    private DespachoLogistico despachoLogistico;

    @Column(name = "direccion_aproximada", length = 240)
    private String direccionAproximada;

    public TelemetriaGPS() {
    }

    public TelemetriaGPS(String dispositivoGpsId, Double latitud, Double longitud, Double altitud,
                         Double velocidad, LocalDateTime fechaHora, DespachoLogistico despachoLogistico,
                         String direccionAproximada) {
        this.dispositivoGpsId = dispositivoGpsId;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.velocidad = velocidad;
        this.fechaHora = fechaHora;
        this.despachoLogistico = despachoLogistico;
        this.direccionAproximada = direccionAproximada;
    }

    public TelemetriaGPS(Long id, String dispositivoGpsId, Double latitud, Double longitud, Double altitud,
                         Double velocidad, LocalDateTime fechaHora, DespachoLogistico despachoLogistico,
                         String direccionAproximada) {
        this(dispositivoGpsId, latitud, longitud, altitud, velocidad, fechaHora, despachoLogistico, direccionAproximada);
        this.setId(id);
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

    public DespachoLogistico getDespachoLogistico() {
        return despachoLogistico;
    }

    public void setDespachoLogistico(DespachoLogistico despachoLogistico) {
        this.despachoLogistico = despachoLogistico;
    }

    public String getDireccionAproximada() {
        return direccionAproximada;
    }

    public void setDireccionAproximada(String direccionAproximada) {
        this.direccionAproximada = direccionAproximada;
    }
}
