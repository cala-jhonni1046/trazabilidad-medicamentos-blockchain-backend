package ies.belgrano.medicamentos.lote;

import java.time.LocalDateTime;

public class LoteRequestDTO {
    private String codigoLote;
    private Long medicamentoId;
    private Long laboratorioId;
    private Double tempMin;
    private Double tempMax;
    private LocalDateTime fechaHoraFabricacion;
    private EstadoLote estado;

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
