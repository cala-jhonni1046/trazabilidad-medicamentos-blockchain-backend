package ies.belgrano.medicamentos.blockchain.registroblockchain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.utils.BaseEntity;

@Entity
@Table(name = "registros_blockchain")
public class RegistroBlockchain extends BaseEntity {

    private String transactionHash;

    private Long bloque;

    private String firmaInspectorANMAT;

    private LocalDateTime timestamp;

    private Boolean confirmado;

    @ManyToOne
    @JoinColumn(name = "lote_id", nullable = true)
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "inspector_id", nullable = true)
    private InspectorANMAT inspector;

    public RegistroBlockchain() {
    }

    public RegistroBlockchain(String transactionHash, Long bloque, String firmaInspectorANMAT, LocalDateTime timestamp, Boolean confirmado, Lote lote, InspectorANMAT inspector) {
        this.transactionHash = transactionHash;
        this.bloque = bloque;
        this.firmaInspectorANMAT = firmaInspectorANMAT;
        this.timestamp = timestamp;
        this.confirmado = confirmado;
        this.lote = lote;
        this.inspector = inspector;
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

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public InspectorANMAT getInspector() {
        return inspector;
    }

    public void setInspector(InspectorANMAT inspector) {
        this.inspector = inspector;
    }
}
