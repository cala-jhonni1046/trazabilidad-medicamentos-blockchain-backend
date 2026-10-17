package ies.belgrano.medicamentos.lote;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoteRequestDTO {

    @NotBlank(message = "El código de lote es obligatorio")
    @Size(max = 80, message = "El código de lote no puede superar los 80 caracteres")
    private String codigoLote;

    @NotNull(message = "El ID de medicamento es obligatorio")
    private Long medicamentoId;

    @NotNull(message = "El ID de laboratorio es obligatorio")
    private Long laboratorioId;

    private Double tempMin;
    private Double tempMax;
    private LocalDateTime fechaHoraFabricacion;
    private EstadoLote estado;

    public LoteRequestDTO() {
    }

    public LoteRequestDTO(String codigoLote, Long medicamentoId, Long laboratorioId, Double tempMin, Double tempMax,
                          LocalDateTime fechaHoraFabricacion, EstadoLote estado) {
        this.codigoLote = codigoLote;
        this.medicamentoId = medicamentoId;
        this.laboratorioId = laboratorioId;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.fechaHoraFabricacion = fechaHoraFabricacion;
        this.estado = estado;
    }

    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }
    public Long getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(Long medicamentoId) { this.medicamentoId = medicamentoId; }
    public Long getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(Long laboratorioId) { this.laboratorioId = laboratorioId; }
    public Double getTempMin() { return tempMin; }
    public void setTempMin(Double tempMin) { this.tempMin = tempMin; }
    public Double getTempMax() { return tempMax; }
    public void setTempMax(Double tempMax) { this.tempMax = tempMax; }
    public LocalDateTime getFechaHoraFabricacion() { return fechaHoraFabricacion; }
    public void setFechaHoraFabricacion(LocalDateTime fechaHoraFabricacion) { this.fechaHoraFabricacion = fechaHoraFabricacion; }
    public EstadoLote getEstado() { return estado; }
    public void setEstado(EstadoLote estado) { this.estado = estado; }
}

