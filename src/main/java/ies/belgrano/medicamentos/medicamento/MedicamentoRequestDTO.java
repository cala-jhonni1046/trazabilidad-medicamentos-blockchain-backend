package ies.belgrano.medicamentos.medicamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MedicamentoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 180, message = "El nombre no puede exceder los 180 caracteres")
    private String nombre;

    @NotBlank(message = "El principio activo es obligatorio")
    @Size(max = 180, message = "El principio activo no puede exceder los 180 caracteres")
    private String principioActivo;

    @NotNull(message = "El campo requiereCadenaFrio es obligatorio")
    private Boolean requiereCadenaFrio;

    public MedicamentoRequestDTO() {
    }

    public MedicamentoRequestDTO(String nombre, String principioActivo, Boolean requiereCadenaFrio) {
        this.nombre = nombre;
        this.principioActivo = principioActivo;
        this.requiereCadenaFrio = requiereCadenaFrio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public Boolean getRequiereCadenaFrio() {
        return requiereCadenaFrio;
    }

    public void setRequiereCadenaFrio(Boolean requiereCadenaFrio) {
        this.requiereCadenaFrio = requiereCadenaFrio;
    }
}

