package ies.belgrano.medicamentos.cuarentena;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CuarentenaRequestDTO {

    @NotBlank(message = "El código de cuarentena es obligatorio.")
    @Size(max = 80, message = "El código de cuarentena no puede superar los 80 caracteres.")
    private String codigoCuarentena;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    @NotBlank(message = "El motivo de cuarentena es obligatorio.")
    @Size(max = 500, message = "El motivo de cuarentena no puede superar los 500 caracteres.")
    private String motivo;

    private EstadoCuarentena estado;

    @NotNull(message = "El ID de lote es obligatorio.")
    private Long loteId;

    private Long inspectorResponsableId;

    @Size(max = 500, message = "La resolución final no puede superar los 500 caracteres.")
    private String resolucionFinal;

    @Size(max = 1000, message = "Las observaciones no pueden superar los 1000 caracteres.")
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
