package ies.belgrano.medicamentos.enlaceCUIT;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "enlaces_cuit")
public class EnlaceCUIT extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "laboratorio_id", nullable = false)
    private Organizacion laboratorio;
    @ManyToOne(optional = false)
    @JoinColumn(name = "distribuidor_id", nullable = false)
    private Organizacion distribuidor;
    @ManyToOne(optional = false)
    @JoinColumn(name = "farmacia_id", nullable = false)
    private Organizacion farmacia;
    @ManyToOne
    @JoinColumn(name = "inspector_aprobador_id")
    private InspectorANMAT inspectorAprobador;
    @Column(name = "numero_disposicion_anmat", length = 80)
    private String numeroDisposicionANMAT;
    @Column(name = "documento_certificado_url", length = 500)
    private String documentoCertificadoUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 24)
    private EstadoEnlaceCuit estado = EstadoEnlaceCuit.PENDIENTE;
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;
    @Column(name = "fecha_aprobacion_anmat")
    private LocalDateTime fechaAprobacionANMAT;

    public Organizacion getLaboratorio() { return laboratorio; }
    public void setLaboratorio(Organizacion laboratorio) { this.laboratorio = laboratorio; }
    public Organizacion getDistribuidor() { return distribuidor; }
    public void setDistribuidor(Organizacion distribuidor) { this.distribuidor = distribuidor; }
    public Organizacion getFarmacia() { return farmacia; }
    public void setFarmacia(Organizacion farmacia) { this.farmacia = farmacia; }
    public InspectorANMAT getInspectorAprobador() { return inspectorAprobador; }
    public void setInspectorAprobador(InspectorANMAT value) { this.inspectorAprobador = value; }
    public String getNumeroDisposicionANMAT() { return numeroDisposicionANMAT; }
    public void setNumeroDisposicionANMAT(String value) { this.numeroDisposicionANMAT = value; }
    public String getDocumentoCertificadoUrl() { return documentoCertificadoUrl; }
    public void setDocumentoCertificadoUrl(String value) { this.documentoCertificadoUrl = value; }
    public EstadoEnlaceCuit getEstado() { return estado; }
    public void setEstado(EstadoEnlaceCuit estado) { this.estado = estado; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime value) { this.fechaSolicitud = value; }
    public LocalDateTime getFechaAprobacionANMAT() { return fechaAprobacionANMAT; }
    public void setFechaAprobacionANMAT(LocalDateTime value) { this.fechaAprobacionANMAT = value; }
}
