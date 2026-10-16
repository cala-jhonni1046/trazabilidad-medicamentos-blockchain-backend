package ies.belgrano.medicamentos.unidadTrazable;

import java.time.LocalDateTime;

public class UnidadTrazableResponseDTO {
    private Long id;
    private String codigoUnicoQR;
    private Long loteId;
    private String codigoLote;
    private EstadoUnidadTrazable estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoUnicoQR() { return codigoUnicoQR; }
    public void setCodigoUnicoQR(String codigoUnicoQR) { this.codigoUnicoQR = codigoUnicoQR; }
    public Long getLoteId() { return loteId; }
    public void setLoteId(Long loteId) { this.loteId = loteId; }
    public String getCodigoLote() { return codigoLote; }
    public void setCodigoLote(String codigoLote) { this.codigoLote = codigoLote; }
    public EstadoUnidadTrazable getEstado() { return estado; }
    public void setEstado(EstadoUnidadTrazable estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
