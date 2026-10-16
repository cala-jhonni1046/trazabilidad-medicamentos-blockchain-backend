package ies.belgrano.medicamentos.unidadTrazable;

public class UnidadTrazableRequestDTO {
    private String codigoUnicoQR;
    private Long loteId;
    private EstadoUnidadTrazable estado;

    public String getCodigoUnicoQR() { return codigoUnicoQR; }
    public void setCodigoUnicoQR(String codigoUnicoQR) { this.codigoUnicoQR = codigoUnicoQR; }
    public Long getLoteId() { return loteId; }
    public void setLoteId(Long loteId) { this.loteId = loteId; }
    public EstadoUnidadTrazable getEstado() { return estado; }
    public void setEstado(EstadoUnidadTrazable estado) { this.estado = estado; }
}
