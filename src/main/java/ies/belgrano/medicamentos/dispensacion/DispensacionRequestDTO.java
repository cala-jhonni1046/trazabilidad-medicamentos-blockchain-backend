package ies.belgrano.medicamentos.dispensacion;

import java.time.LocalDateTime;

public class DispensacionRequestDTO {
    private String recetaNumero;
    private String dniPaciente;
    private Long farmaciaId;
    private Long unidadTrazableId;
    private LocalDateTime fechaDispensacion;
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
