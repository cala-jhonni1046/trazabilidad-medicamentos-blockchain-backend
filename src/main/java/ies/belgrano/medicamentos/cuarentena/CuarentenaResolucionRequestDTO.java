package ies.belgrano.medicamentos.cuarentena;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CuarentenaResolucionRequestDTO {

    @NotNull(message = "El estado de resolución es obligatorio.")
    private EstadoCuarentena estado;

    @Size(max = 500, message = "La resolución final no puede superar los 500 caracteres.")
    private String resolucionFinal;

    private LocalDateTime fechaFin;

    private Long inspectorResponsableId;

    @Size(max = 1000, message = "Las observaciones no pueden superar los 1000 caracteres.")
    private String observaciones;

    public CuarentenaResolucionRequestDTO() {
    }

    public CuarentenaResolucionRequestDTO(EstadoCuarentena estado, String resolucionFinal, LocalDateTime fechaFin,
                                         Long inspectorResponsableId, String observaciones) {
        this.estado = estado;
        this.resolucionFinal = resolucionFinal;
        this.fechaFin = fechaFin;
        this.inspectorResponsableId = inspectorResponsableId;
        this.observaciones = observaciones;
    }

    public EstadoCuarentena getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuarentena estado) {
        this.estado = estado;
    }

    public String getResolucionFinal() {
        return resolucionFinal;
    }

    public void setResolucionFinal(String resolucionFinal) {
        this.resolucionFinal = resolucionFinal;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getInspectorResponsableId() {
        return inspectorResponsableId;
    }

    public void setInspectorResponsableId(Long inspectorResponsableId) {
        this.inspectorResponsableId = inspectorResponsableId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
