package com.medichain.logistics.telemetriagps;

// Importaciones para JPA Entity y tipos Java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

// Entidad JPA que representa las coordenadas de rastreo GPS en la base de datos
@Entity
@Table(name = "telemetria_gps")
public class TelemetriaGPS {

    // Clave primaria generada automáticamente mediante UUID
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Identificador del despacho logístico rastreado
    private UUID despachoId;

    // Patente o vehículo asociado al reporte de geolocalización
    private String patenteCamion;

    // Latitud geográfica reportada por el dispositivo GPS
    private Double latitud;

    // Longitud geográfica reportada por el dispositivo GPS
    private Double longitud;

    // Velocidad actual de desplazamiento expresada en km/h
    private Double velocidad;

    // Marca de tiempo exacta del evento de ubicación GPS
    private LocalDateTime timestamp;

    // Constructor sin argumentos exigido por JPA
    public TelemetriaGPS() {
    }

    // Constructor completo con todos los parámetros
    public TelemetriaGPS(UUID id, UUID despachoId, String patenteCamion, Double latitud, Double longitud, Double velocidad, LocalDateTime timestamp) {
        this.id = id;
        this.despachoId = despachoId;
        this.patenteCamion = patenteCamion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.velocidad = velocidad;
        this.timestamp = timestamp;
    }

    // Métodos de acceso Getters y Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDespachoId() {
        return despachoId;
    }

    public void setDespachoId(UUID despachoId) {
        this.despachoId = despachoId;
    }

    public String getPatenteCamion() {
        return patenteCamion;
    }

    public void setPatenteCamion(String patenteCamion) {
        this.patenteCamion = patenteCamion;
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

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
