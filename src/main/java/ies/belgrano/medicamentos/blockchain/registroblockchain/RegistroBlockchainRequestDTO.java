package ies.belgrano.medicamentos.blockchain.registroblockchain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;

public class RegistroBlockchainRequestDTO {

    @Size(max = 128, message = "El hash de transacción no puede superar los 128 caracteres")
    private String transactionHash;

    private Long bloque;

    @Size(max = 512, message = "La firma de inspector no puede superar los 512 caracteres")
    private String firmaInspectorANMAT;

    private LocalDateTime timestamp;
    private Boolean confirmado;

    public RegistroBlockchainRequestDTO() {
    }

    public RegistroBlockchainRequestDTO(String transactionHash, Long bloque, String firmaInspectorANMAT, LocalDateTime timestamp, Boolean confirmado) {
        this.transactionHash = transactionHash;
        this.bloque = bloque;
        this.firmaInspectorANMAT = firmaInspectorANMAT;
        this.timestamp = timestamp;
        this.confirmado = confirmado;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public Long getBloque() {
        return bloque;
    }

    public void setBloque(Long bloque) {
        this.bloque = bloque;
    }

    public String getFirmaInspectorANMAT() {
        return firmaInspectorANMAT;
    }

    public void setFirmaInspectorANMAT(String firmaInspectorANMAT) {
        this.firmaInspectorANMAT = firmaInspectorANMAT;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }
}
