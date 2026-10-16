package ies.belgrano.medicamentos.recepcionfarmacia;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recepciones_farmacia")
public class RecepcionFarmacia extends BaseEntity {
    @Column(name = "numero_comprobante", nullable = false, unique = true, length = 80)
    private String numeroComprobante;
    @Column(name = "fecha_hora_recepcion", nullable = false)
    private LocalDateTime fechaHoraRecepcion;
    @ManyToOne(optional = false)
    @JoinColumn(name = "farmacia_id", nullable = false)
    private Organizacion farmacia;
    @Column(name = "farmaceutico_responsable", length = 160)
    private String farmaceuticoResponsable;
    @ManyToOne(optional = false)
    @JoinColumn(name = "despacho_logistico_id", nullable = false)
    private DespachoLogistico despachoLogistico;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_conformidad", nullable = false, length = 24)
    private EstadoRecepcion estadoConformidad;
    @Column(name = "cadena_frio_intacta", nullable = false)
    private Boolean cadenaFrioIntacta;
    @Column(name = "observaciones", length = 1000)
    private String observaciones;
    public String getNumeroComprobante() { return numeroComprobante; }
    public void setNumeroComprobante(String value) { this.numeroComprobante = value; }
    public LocalDateTime getFechaHoraRecepcion() { return fechaHoraRecepcion; }
    public void setFechaHoraRecepcion(LocalDateTime value) { this.fechaHoraRecepcion = value; }
    public Organizacion getFarmacia() { return farmacia; }
    public void setFarmacia(Organizacion value) { this.farmacia = value; }
    public String getFarmaceuticoResponsable() { return farmaceuticoResponsable; }
    public void setFarmaceuticoResponsable(String value) { this.farmaceuticoResponsable = value; }
    public DespachoLogistico getDespachoLogistico() { return despachoLogistico; }
    public void setDespachoLogistico(DespachoLogistico value) { this.despachoLogistico = value; }
    public EstadoRecepcion getEstadoConformidad() { return estadoConformidad; }
    public void setEstadoConformidad(EstadoRecepcion value) { this.estadoConformidad = value; }
    public Boolean getCadenaFrioIntacta() { return cadenaFrioIntacta; }
    public void setCadenaFrioIntacta(Boolean value) { this.cadenaFrioIntacta = value; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
}
