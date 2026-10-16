package ies.belgrano.medicamentos.medicamento;

import java.util.ArrayList;
import java.util.List;

import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicamentos")
public class Medicamento extends BaseEntity {
    @Column(name = "nombre", nullable = false, length = 180)
    private String nombre;

    @Column(name = "principio_activo", nullable = false, length = 180)
    private String principioActivo;

    @Column(name = "requiere_cadena_frio", nullable = false)
    private boolean requiereCadenaFrio;

    @OneToMany(mappedBy = "medicamento")
    private List<Lote> lotes = new ArrayList<>();

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPrincipioActivo() { return principioActivo; }
    public void setPrincipioActivo(String principioActivo) { this.principioActivo = principioActivo; }
    public boolean isRequiereCadenaFrio() { return requiereCadenaFrio; }
    public void setRequiereCadenaFrio(boolean requiereCadenaFrio) { this.requiereCadenaFrio = requiereCadenaFrio; }
    public List<Lote> getLotes() { return lotes; }
    public void setLotes(List<Lote> lotes) { this.lotes = lotes; }
}
