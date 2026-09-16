package com.medichain.logistics.despachologistico;

// Importaciones necesarias de JPA y Java Time/UUID
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

// Indica que esta clase es una entidad persistente de JPA (representa una tabla en la BD)
@Entity
// Define el nombre exacto de la tabla en la base de datos MySQL
@Table(name = "despachos_logisticos")
public class DespachoLogistico {

    // Marca este atributo como la clave primaria (Primary Key) de la entidad
    @Id
    // Genera automáticamente un valor único de tipo UUID al crear un nuevo registro
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Identificador único del lote de medicamentos asociado al despacho
    private UUID loteId;

    // Patente o dominio del vehículo transportista
    private String patenteVehiculo;

    // CUIT de la empresa de transporte responsable
    private String cuitTransportista;

    // Fecha y hora exacta en que el transportista asumió la custodia del lote
    private LocalDateTime fechaHoraCustodiaAsumida;

    // Constructor vacío requerido obligatoriamente por JPA / Hibernate
    public DespachoLogistico() {
    }

    // Constructor con todos los atributos para crear instancias de forma manual
    public DespachoLogistico(UUID id, UUID loteId, String patenteVehiculo, String cuitTransportista, LocalDateTime fechaHoraCustodiaAsumida) {
        this.id = id;
        this.loteId = loteId;
        this.patenteVehiculo = patenteVehiculo;
        this.cuitTransportista = cuitTransportista;
        this.fechaHoraCustodiaAsumida = fechaHoraCustodiaAsumida;
    }

    // Métodos Getters y Setters para acceder y modificar las propiedades privadas

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getLoteId() {
        return loteId;
    }

    public void setLoteId(UUID loteId) {
        this.loteId = loteId;
    }

    public String getPatenteVehiculo() {
        return patenteVehiculo;
    }

    public void setPatenteVehiculo(String patenteVehiculo) {
        this.patenteVehiculo = patenteVehiculo;
    }

    public String getCuitTransportista() {
        return cuitTransportista;
    }

    public void setCuitTransportista(String cuitTransportista) {
        this.cuitTransportista = cuitTransportista;
    }

    public LocalDateTime getFechaHoraCustodiaAsumida() {
        return fechaHoraCustodiaAsumida;
    }

    public void setFechaHoraCustodiaAsumida(LocalDateTime fechaHoraCustodiaAsumida) {
        this.fechaHoraCustodiaAsumida = fechaHoraCustodiaAsumida;
    }
}
