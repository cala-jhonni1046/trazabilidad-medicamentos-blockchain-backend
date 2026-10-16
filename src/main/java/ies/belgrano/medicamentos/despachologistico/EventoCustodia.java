package ies.belgrano.medicamentos.despachologistico;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/** An academic, append-oriented traceability event; hashes are demonstration metadata, not a blockchain. */
@Entity
@Table(name = "eventos_custodia")
public class EventoCustodia extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento", nullable = false, length = 32)
    private TipoEventoCustodia tipo;
    @Column(name = "fecha_hora_evento_utc", nullable = false)
    private LocalDateTime fechaHoraEvento;
    @ManyToOne(optional = false)
    @JoinColumn(name = "despacho_logistico_id", nullable = false)
    private DespachoLogistico despacho;
    @ManyToOne
    @JoinColumn(name = "organizacion_actor_id")
    private Organizacion organizacionActor;
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @Column(name = "hash_anterior", length = 128)
    private String hashAnterior;
    @Column(name = "hash_evento", nullable = false, length = 128)
    private String hashEvento;

    public TipoEventoCustodia getTipo() { return tipo; }
    public void setTipo(TipoEventoCustodia tipo) { this.tipo = tipo; }
    public LocalDateTime getFechaHoraEvento() { return fechaHoraEvento; }
    public void setFechaHoraEvento(LocalDateTime fechaHoraEvento) { this.fechaHoraEvento = fechaHoraEvento; }
    public DespachoLogistico getDespacho() { return despacho; }
    public void setDespacho(DespachoLogistico despacho) { this.despacho = despacho; }
    public Organizacion getOrganizacionActor() { return organizacionActor; }
    public void setOrganizacionActor(Organizacion organizacionActor) { this.organizacionActor = organizacionActor; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getHashAnterior() { return hashAnterior; }
    public void setHashAnterior(String hashAnterior) { this.hashAnterior = hashAnterior; }
    public String getHashEvento() { return hashEvento; }
    public void setHashEvento(String hashEvento) { this.hashEvento = hashEvento; }
}
