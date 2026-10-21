package ies.belgrano.medicamentos.reporteciudadano;

public class ReporteResolucionRequestDTO {

    private Long inspectorAsignadoId;

    public ReporteResolucionRequestDTO() {
    }

    public ReporteResolucionRequestDTO(Long inspectorAsignadoId) {
        this.inspectorAsignadoId = inspectorAsignadoId;
    }

    public Long getInspectorAsignadoId() {
        return inspectorAsignadoId;
    }

    public void setInspectorAsignadoId(Long inspectorAsignadoId) {
        this.inspectorAsignadoId = inspectorAsignadoId;
    }
}
