package ies.belgrano.medicamentos.lote;

public class GeneracionUnidadesResponseDTO {

    private Long loteId;
    private String codigoLote;
    private Integer cantidadGenerada;
    private String primerCodigo;
    private String ultimoCodigo;

    public GeneracionUnidadesResponseDTO() {
    }

    public GeneracionUnidadesResponseDTO(Long loteId, String codigoLote, Integer cantidadGenerada, String primerCodigo, String ultimoCodigo) {
        this.loteId = loteId;
        this.codigoLote = codigoLote;
        this.cantidadGenerada = cantidadGenerada;
        this.primerCodigo = primerCodigo;
        this.ultimoCodigo = ultimoCodigo;
    }

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public Integer getCantidadGenerada() {
        return cantidadGenerada;
    }

    public void setCantidadGenerada(Integer cantidadGenerada) {
        this.cantidadGenerada = cantidadGenerada;
    }

    public String getPrimerCodigo() {
        return primerCodigo;
    }

    public void setPrimerCodigo(String primerCodigo) {
        this.primerCodigo = primerCodigo;
    }

    public String getUltimoCodigo() {
        return ultimoCodigo;
    }

    public void setUltimoCodigo(String ultimoCodigo) {
        this.ultimoCodigo = ultimoCodigo;
    }
}
