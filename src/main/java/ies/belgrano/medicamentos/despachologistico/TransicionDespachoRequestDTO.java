package ies.belgrano.medicamentos.despachologistico;

import jakarta.validation.constraints.Size;

public class TransicionDespachoRequestDTO {

    @Size(max = 1000, message = "Las observaciones no pueden superar los 1000 caracteres.")
    private String observaciones;

    public TransicionDespachoRequestDTO() {
    }

    public TransicionDespachoRequestDTO(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
