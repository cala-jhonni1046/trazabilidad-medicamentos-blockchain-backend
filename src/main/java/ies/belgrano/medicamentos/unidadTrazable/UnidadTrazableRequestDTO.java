package ies.belgrano.medicamentos.unidadTrazable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UnidadTrazableRequestDTO {

    @NotBlank(message = "El código QR es obligatorio y no puede estar vacío.")
    @Size(max = 160, message = "El código QR no puede superar los 160 caracteres.")
    private String codigoUnicoQR;

    @NotNull(message = "El ID de lote es obligatorio.")
    private Long loteId;

    private EstadoUnidadTrazable estado;

    public UnidadTrazableRequestDTO() {
    }

    public UnidadTrazableRequestDTO(String codigoUnicoQR, Long loteId) {
        this.codigoUnicoQR = codigoUnicoQR;
        this.loteId = loteId;
    }

    public String getCodigoUnicoQR() {
        return codigoUnicoQR;
    }

    public void setCodigoUnicoQR(String codigoUnicoQR) {
        this.codigoUnicoQR = codigoUnicoQR;
    }

    public String getCodigoQr() {
        return codigoUnicoQR;
    }

    public void setCodigoQr(String codigoQr) {
        if (this.codigoUnicoQR == null || this.codigoUnicoQR.isBlank()) {
            this.codigoUnicoQR = codigoQr;
        }
    }

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public EstadoUnidadTrazable getEstado() {
        return estado;
    }

    public void setEstado(EstadoUnidadTrazable estado) {
        this.estado = estado;
    }
}
