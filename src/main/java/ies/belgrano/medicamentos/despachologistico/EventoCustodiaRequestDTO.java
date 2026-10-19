package ies.belgrano.medicamentos.despachologistico;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EventoCustodiaRequestDTO {

    @NotNull(message = "El tipo de evento es obligatorio.")
    private TipoEventoCustodia tipo;

    private LocalDateTime fechaHoraEvento;

    private Long organizacionActorId;

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres.")
    private String descripcion;

    public EventoCustodiaRequestDTO() {
    }

    public EventoCustodiaRequestDTO(TipoEventoCustodia tipo, LocalDateTime fechaHoraEvento,
                                   Long organizacionActorId, String descripcion) {
        this.tipo = tipo;
        this.fechaHoraEvento = fechaHoraEvento;
        this.organizacionActorId = organizacionActorId;
        this.descripcion = descripcion;
    }

    public TipoEventoCustodia getTipo() {
        return tipo;
    }

    public void setTipo(TipoEventoCustodia tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHoraEvento() {
        return fechaHoraEvento;
    }

    public void setFechaHoraEvento(LocalDateTime fechaHoraEvento) {
        this.fechaHoraEvento = fechaHoraEvento;
    }

    public Long getOrganizacionActorId() {
        return organizacionActorId;
    }

    public void setOrganizacionActorId(Long organizacionActorId) {
        this.organizacionActorId = organizacionActorId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
