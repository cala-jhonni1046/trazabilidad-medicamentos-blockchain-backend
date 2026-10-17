package ies.belgrano.medicamentos.organizacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrganizacionRequestDTO {

    @NotBlank(message = "El CUIT es obligatorio")
    @Pattern(regexp = "^(\\d{11}|\\d{2}-\\d{8}-\\d)$", message = "El CUIT debe contener 11 dígitos numéricos o formato XX-XXXXXXXX-X")
    private String cuit;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 160, message = "El nombre no puede superar los 160 caracteres")
    private String nombre;

    @NotNull(message = "El tipo de organización es obligatorio")
    private TipoOrganizacion tipo;

    public OrganizacionRequestDTO() {
    }

    public OrganizacionRequestDTO(String cuit, String nombre, TipoOrganizacion tipo) {
        this.cuit = cuit;
        this.nombre = nombre;
        this.tipo = tipo;
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
}
