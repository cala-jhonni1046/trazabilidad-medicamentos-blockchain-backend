package ies.belgrano.medicamentos.despachologistico;

import java.time.LocalDateTime;

public class EventoCustodiaResponseDTO {

    private Long id;
    private TipoEventoCustodia tipo;
    private LocalDateTime fechaHoraEvento;
    private Long despachoLogisticoId;
    private Long organizacionActorId;
    private String organizacionActorNombre;
    private String organizacionActorCuit;
    private String descripcion;
    private String hashAnterior;
    private String hashEvento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public EventoCustodiaResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDespachoLogisticoId() {
        return despachoLogisticoId;
    }

    public void setDespachoLogisticoId(Long despachoLogisticoId) {
        this.despachoLogisticoId = despachoLogisticoId;
    }

    public Long getOrganizacionActorId() {
        return organizacionActorId;
    }

    public void setOrganizacionActorId(Long organizacionActorId) {
        this.organizacionActorId = organizacionActorId;
    }

    public String getOrganizacionActorNombre() {
        return organizacionActorNombre;
    }

    public void setOrganizacionActorNombre(String organizacionActorNombre) {
        this.organizacionActorNombre = organizacionActorNombre;
    }

    public String getOrganizacionActorCuit() {
        return organizacionActorCuit;
    }

    public void setOrganizacionActorCuit(String organizacionActorCuit) {
        this.organizacionActorCuit = organizacionActorCuit;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHashAnterior() {
        return hashAnterior;
    }

    public void setHashAnterior(String hashAnterior) {
        this.hashAnterior = hashAnterior;
    }

    public String getHashEvento() {
        return hashEvento;
    }

    public void setHashEvento(String hashEvento) {
        this.hashEvento = hashEvento;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
