package ies.belgrano.medicamentos.medicamento;

public class MedicamentoRequestDTO {

    private String nombre;
    private String principioActivo;
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
