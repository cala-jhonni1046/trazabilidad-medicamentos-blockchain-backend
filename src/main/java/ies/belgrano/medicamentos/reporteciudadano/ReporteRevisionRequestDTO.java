package ies.belgrano.medicamentos.reporteciudadano;

public class ReporteRevisionRequestDTO {

    private Long inspectorAsignadoId;

    public ReporteRevisionRequestDTO() {
    }

    public ReporteRevisionRequestDTO(Long inspectorAsignadoId) {
        this.inspectorAsignadoId = inspectorAsignadoId;
    }

    public Long getInspectorAsignadoId() {
        return inspectorAsignadoId;
    }

    public void setInspectorAsignadoId(Long inspectorAsignadoId) {
        this.inspectorAsignadoId = inspectorAsignadoId;
    }
}
