package ies.belgrano.medicamentos.dispensacion;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.utils.BaseEntity;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import jakarta.persistence.Column;

@Entity
@Table(name = "dispensaciones")
public class Dispensacion extends BaseEntity {

    @Column(name = "receta_numero", length = 100)
    private String recetaNumero;

    @Column(name = "dni_paciente", length = 16)
    private String dniPaciente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "farmacia_id", nullable = false)
    private Organizacion farmacia;

    @Column(name = "fecha_dispensacion_utc", nullable = false)
    private LocalDateTime fechaDispensacion;

    @OneToOne
    @JoinColumn(name = "unidad_trazable_id", unique = true, nullable = false)
    private UnidadTrazable unidadTrazable;

    public Dispensacion() {
    }

    public Dispensacion(String recetaNumero, String dniPaciente, Organizacion farmacia, LocalDateTime fechaDispensacion, UnidadTrazable unidadTrazable) {
        this.recetaNumero = recetaNumero;
        this.dniPaciente = dniPaciente;
        this.farmacia = farmacia;
        this.fechaDispensacion = fechaDispensacion;
        this.unidadTrazable = unidadTrazable;
    }

    public String getRecetaNumero() {
        return recetaNumero;
    }

    public void setRecetaNumero(String recetaNumero) {
        this.recetaNumero = recetaNumero;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public Organizacion getFarmacia() { return farmacia; }
    public void setFarmacia(Organizacion farmacia) { this.farmacia = farmacia; }

    public LocalDateTime getFechaDispensacion() {
        return fechaDispensacion;
    }

    public void setFechaDispensacion(LocalDateTime fechaDispensacion) {
        this.fechaDispensacion = fechaDispensacion;
    }

    public UnidadTrazable getUnidadTrazable() {
        return unidadTrazable;
    }

    public void setUnidadTrazable(UnidadTrazable unidadTrazable) {
        this.unidadTrazable = unidadTrazable;
    }
}
