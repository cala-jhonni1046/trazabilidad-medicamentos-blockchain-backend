package ies.belgrano.medicamentos.lote;

import java.time.LocalDateTime;

public class LoteResponseDTO {
    private Long id;
    private String codigoLote;
    private Long medicamentoId;
    private String medicamentoNombre;
    private Long laboratorioId;
    private String laboratorioNombre;
    private String laboratorioCuit;
    private Double tempMin;
    private Double tempMax;
    private LocalDateTime fechaHoraFabricacion;
    private EstadoLote estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }
    public Long getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(Long medicamentoId) { this.medicamentoId = medicamentoId; }
    public String getMedicamentoNombre() { return medicamentoNombre; }
    public void setMedicamentoNombre(String medicamentoNombre) { this.medicamentoNombre = medicamentoNombre; }
    public Long getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(Long laboratorioId) { this.laboratorioId = laboratorioId; }
    public String getLaboratorioNombre() { return laboratorioNombre; }
    public void setLaboratorioNombre(String laboratorioNombre) { this.laboratorioNombre = laboratorioNombre; }
    public String getLaboratorioCuit() { return laboratorioCuit; }
    public void setLaboratorioCuit(String laboratorioCuit) { this.laboratorioCuit = laboratorioCuit; }
    public Double getTempMin() { return tempMin; }
    public void setTempMin(Double tempMin) { this.tempMin = tempMin; }
    public Double getTempMax() { return tempMax; }
    public void setTempMax(Double tempMax) { this.tempMax = tempMax; }
    public LocalDateTime getFechaHoraFabricacion() { return fechaHoraFabricacion; }
    public void setFechaHoraFabricacion(LocalDateTime fechaHoraFabricacion) { this.fechaHoraFabricacion = fechaHoraFabricacion; }
    public EstadoLote getEstado() { return estado; }
    public void setEstado(EstadoLote estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
