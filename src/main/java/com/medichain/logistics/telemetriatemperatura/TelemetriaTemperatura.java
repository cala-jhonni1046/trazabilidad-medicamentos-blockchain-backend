package com.medichain.logistics.telemetriatemperatura;

// Importaciones de annotations JPA y clases estándar de Java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

// Anotación que marca esta clase como una entidad de base de datos JPA
@Entity
// Nombre de la tabla correspondiente en la base de datos
@Table(name = "telemetria_temperatura")
public class TelemetriaTemperatura {

    // Clave primaria identificadora de la lectura de temperatura
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Identificador único del despacho logístico al cual pertenece la lectura
    private UUID despachoId;

    // Identificador único del sensor de temperatura físico
    private String sensorId;

    // Valor de la temperatura registrada en grados centígrados
    private Double temperatura;

    // Fecha y hora exacta de la toma de lectura del sensor
    private LocalDateTime timestamp;

    // Indicador booleano que señala si la temperatura excedió el rango permitido
    private Boolean fueraDeRango;

    // Constructor sin parámetros requerido por JPA
    public TelemetriaTemperatura() {
    }

    // Constructor parametrizado completo
    public TelemetriaTemperatura(UUID id, UUID despachoId, String sensorId, Double temperatura, LocalDateTime timestamp, Boolean fueraDeRango) {
        this.id = id;
        this.despachoId = despachoId;
        this.sensorId = sensorId;
        this.temperatura = temperatura;
        this.timestamp = timestamp;
        this.fueraDeRango = fueraDeRango;
    }

    // Getters y Setters de los campos

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getFueraDeRango() {
        return fueraDeRango;
    }

    public void setFueraDeRango(Boolean fueraDeRango) {
        this.fueraDeRango = fueraDeRango;
    }
}
