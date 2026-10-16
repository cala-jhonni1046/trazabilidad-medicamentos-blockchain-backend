package ies.belgrano.medicamentos.cuarentena;

import java.time.LocalDateTime;

public class CuarentenaResponseDTO {
    private Long id;
    private String codigoCuarentena;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String motivo;
    private EstadoCuarentena estado;
    private Long loteId;
    private String codigoLote;
    private Long inspectorResponsableId;
    private String inspectorNombre;
    private String resolucionFinal;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }
    public String getCodigoCuarentena() { return codigoCuarentena; }
    public void setCodigoCuarentena(String value) { this.codigoCuarentena = value; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime value) { this.fechaInicio = value; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime value) { this.fechaFin = value; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String value) { this.motivo = value; }
    public EstadoCuarentena getEstado() { return estado; }
    public void setEstado(EstadoCuarentena value) { this.estado = value; }
    public Long getLoteId() { return loteId; }
    public void setLoteId(Long value) { this.loteId = value; }
    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String value) { this.codigoLote = value; }
    public Long getInspectorResponsableId() { return inspectorResponsableId; }
    public void setInspectorResponsableId(Long value) { this.inspectorResponsableId = value; }
    public String getInspectorNombre() { return inspectorNombre; }
    public void setInspectorNombre(String value) { this.inspectorNombre = value; }
    public String getResolucionFinal() { return resolucionFinal; }
    public void setResolucionFinal(String value) { this.resolucionFinal = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime value) { this.fechaCreacion = value; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime value) { this.fechaActualizacion = value; }
}
