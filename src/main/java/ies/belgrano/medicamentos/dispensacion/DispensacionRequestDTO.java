package ies.belgrano.medicamentos.dispensacion;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DispensacionRequestDTO {

    @Size(max = 100, message = "El número de receta no puede superar los 100 caracteres.")
    private String recetaNumero;

    @Size(max = 16, message = "El DNI del paciente no puede superar los 16 caracteres.")
    private String dniPaciente;

    @NotNull(message = "El ID de farmacia es obligatorio.")
    private Long farmaciaId;

    @NotNull(message = "El ID de unidad trazable es obligatorio.")
    private Long unidadTrazableId;

    private LocalDateTime fechaDispensacion;

    public DispensacionRequestDTO() {
    }

    public DispensacionRequestDTO(Long unidadTrazableId, Long farmaciaId) {
        this.unidadTrazableId = unidadTrazableId;
        this.farmaciaId = farmaciaId;
    }

    public String getRecetaNumero() { return recetaNumero; }
    public void setRecetaNumero(String value) { this.recetaNumero = value; }
    public String getDniPaciente() { return dniPaciente; }
    public void setDniPaciente(String value) { this.dniPaciente = value; }
    public Long getFarmaciaId() { return farmaciaId; }
    public void setFarmaciaId(Long value) { this.farmaciaId = value; }
    public Long getUnidadTrazableId() { return unidadTrazableId; }
    public void setUnidadTrazableId(Long value) { this.unidadTrazableId = value; }
    public LocalDateTime getFechaDispensacion() { return fechaDispensacion; }
    public void setFechaDispensacion(LocalDateTime value) { this.fechaDispensacion = value; }
}
