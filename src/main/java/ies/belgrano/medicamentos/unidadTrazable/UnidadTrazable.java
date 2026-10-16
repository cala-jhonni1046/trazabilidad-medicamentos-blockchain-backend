package ies.belgrano.medicamentos.unidadTrazable;

import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.dispensacion.Dispensacion;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;

@Entity
@Table(name = "unidades_trazables")
public class UnidadTrazable extends BaseEntity {
    @Column(name = "codigo_qr", nullable = false, unique = true, length = 160)
    private String codigoUnicoQR;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 24)
    private EstadoUnidadTrazable estado = EstadoUnidadTrazable.DISPONIBLE;

    @OneToOne(mappedBy = "unidadTrazable")
    private Dispensacion dispensacion;

    public String getCodigoUnicoQR() { return codigoUnicoQR; }
    public void setCodigoUnicoQR(String codigoUnicoQR) { this.codigoUnicoQR = codigoUnicoQR; }
    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
    public EstadoUnidadTrazable getEstado() { return estado; }
    public void setEstado(EstadoUnidadTrazable estado) { this.estado = estado; }
    public Dispensacion getDispensacion() { return dispensacion; }
    public void setDispensacion(Dispensacion dispensacion) { this.dispensacion = dispensacion; }
}
