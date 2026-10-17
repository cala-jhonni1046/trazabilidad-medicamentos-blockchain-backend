package ies.belgrano.medicamentos.organizacion;

import java.time.LocalDateTime;

public class OrganizacionResponseDTO {

    private Long id;
    private String cuit;
    private String nombre;
    private TipoOrganizacion tipo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public OrganizacionResponseDTO() {
    }

    public OrganizacionResponseDTO(Long id, String cuit, String nombre, TipoOrganizacion tipo,
                                   LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.cuit = cuit;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoOrganizacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoOrganizacion tipo) {
        this.tipo = tipo;
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
