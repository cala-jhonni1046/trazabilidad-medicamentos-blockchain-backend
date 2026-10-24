package ies.belgrano.medicamentos.lote;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GenerarUnidadesRequestDTO {

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser como mínimo 1")
    @Max(value = 1000, message = "La cantidad no puede superar 1000")
    private Integer cantidad;

    public GenerarUnidadesRequestDTO() {
    }

    public GenerarUnidadesRequestDTO(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
