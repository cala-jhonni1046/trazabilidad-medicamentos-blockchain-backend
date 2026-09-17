package com.medichain.blockchain.registroblockchain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "registros_blockchain")
public class RegistroBlockchain {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String transactionHash;

    private Long bloque;

    private String firmaInspectorANMAT;

    private LocalDateTime timestamp;

    private Boolean confirmado;

    public RegistroBlockchain() {
    }

    public RegistroBlockchain(UUID id, String transactionHash, Long bloque, String firmaInspectorANMAT, LocalDateTime timestamp, Boolean confirmado) {
        this.id = id;
        this.transactionHash = transactionHash;
        this.bloque = bloque;
        this.firmaInspectorANMAT = firmaInspectorANMAT;
        this.timestamp = timestamp;
        this.confirmado = confirmado;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
