package ies.belgrano.medicamentos.blockchain.registroblockchain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.utils.BaseEntity;

@Entity
@Table(name = "registros_blockchain")
/** Academic simulation metadata only; this entity does not connect to a blockchain network. */
public class RegistroBlockchain extends BaseEntity {

    @Column(name = "transaction_hash", length = 128)
    private String transactionHash;

    @Column(name = "numero_bloque")
    private Long bloque;

    @Column(name = "firma_inspector_anmat", length = 512)
    private String firmaInspectorANMAT;

    @Column(name = "fecha_hora_utc")
    private LocalDateTime timestamp;

    @Column(name = "confirmado")
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
