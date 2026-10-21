package ies.belgrano.medicamentos.reporteciudadano;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReporteCiudadanoRequestDTO {

    @NotBlank(message = "El motivo del reporte es obligatorio.")
    @Size(max = 1000, message = "El motivo del reporte no puede superar los 1000 caracteres.")
    private String motivoReporte;

    private LocalDateTime fechaHoraReporte;

    @Size(max = 160, message = "El código QR no puede superar los 160 caracteres.")
    private String codigoUnicoQR;

    private Long unidadTrazableId;
    private Long inspectorAsignadoId;
    private EstadoReporte estado;

    public String getMotivoReporte() {
        return motivoReporte;
    }

    public void setMotivoReporte(String motivoReporte) {
        this.motivoReporte = motivoReporte;
    }

    public LocalDateTime getFechaHoraReporte() {
        return fechaHoraReporte;
    }

    public void setFechaHoraReporte(LocalDateTime fechaHoraReporte) {
        this.fechaHoraReporte = fechaHoraReporte;
    }

    public String getCodigoUnicoQR() {
        return codigoUnicoQR;
    }

    public void setCodigoUnicoQR(String codigoUnicoQR) {
        this.codigoUnicoQR = codigoUnicoQR;
    }

    public Long getUnidadTrazableId() {
        return unidadTrazableId;
    }

    public void setUnidadTrazableId(Long unidadTrazableId) {
        this.unidadTrazableId = unidadTrazableId;
    }

    public Long getInspectorAsignadoId() {
        return inspectorAsignadoId;
    }

    public void setInspectorAsignadoId(Long inspectorAsignadoId) {
        this.inspectorAsignadoId = inspectorAsignadoId;
    }

    public EstadoReporte getEstado() {
        return estado;
    }

    public void setEstado(EstadoReporte estado) {
        this.estado = estado;
    }
}
