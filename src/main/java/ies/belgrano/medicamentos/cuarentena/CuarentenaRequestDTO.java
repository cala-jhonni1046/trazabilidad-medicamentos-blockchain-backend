package ies.belgrano.medicamentos.cuarentena;

import java.time.LocalDateTime;

public class CuarentenaRequestDTO {
    private String codigoCuarentena;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String motivo;
    private EstadoCuarentena estado;
    private Long loteId;
    private Long inspectorResponsableId;
    private String resolucionFinal;
    private String observaciones;
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
    public Long getInspectorResponsableId() { return inspectorResponsableId; }
    public void setInspectorResponsableId(Long value) { this.inspectorResponsableId = value; }
    public String getResolucionFinal() { return resolucionFinal; }
    public void setResolucionFinal(String value) { this.resolucionFinal = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
}
