package ies.belgrano.medicamentos.cuarentena;

import java.time.LocalDateTime;

public class CuarentenaResponseDTO {

    private Long id;
    private String codigoCuarentena;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String motivo;
    private String estado;
    private String responsable;
    private String ubicacionFisica;
    private Long loteId;
    private Long unidadTrazableId;
    private String resolucionFinal;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public CuarentenaResponseDTO() {
    }

    public CuarentenaResponseDTO(Long id, String codigoCuarentena, LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                 String motivo, String estado, String responsable, String ubicacionFisica,
                                 Long loteId, Long unidadTrazableId, String resolucionFinal, String observaciones,
                                 LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.codigoCuarentena = codigoCuarentena;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.motivo = motivo;
        this.estado = estado;
        this.responsable = responsable;
        this.ubicacionFisica = ubicacionFisica;
        this.loteId = loteId;
        this.unidadTrazableId = unidadTrazableId;
        this.resolucionFinal = resolucionFinal;
        this.observaciones = observaciones;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoCuarentena() {
        return codigoCuarentena;
    }

    public void setCodigoCuarentena(String codigoCuarentena) {
        this.codigoCuarentena = codigoCuarentena;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public Long getUnidadTrazableId() {
        return unidadTrazableId;
    }

    public void setUnidadTrazableId(Long unidadTrazableId) {
        this.unidadTrazableId = unidadTrazableId;
    }

    public String getResolucionFinal() {
        return resolucionFinal;
    }

    public void setResolucionFinal(String resolucionFinal) {
        this.resolucionFinal = resolucionFinal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
