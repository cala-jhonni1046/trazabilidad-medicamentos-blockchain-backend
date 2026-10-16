package ies.belgrano.medicamentos.blockchain.registroblockchain;

import java.time.LocalDateTime;

public class RegistroBlockchainRequestDTO {

    private String transactionHash;
    private Long bloque;
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
