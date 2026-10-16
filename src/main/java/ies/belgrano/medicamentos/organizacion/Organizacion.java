package ies.belgrano.medicamentos.organizacion;

import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizaciones")
public class Organizacion extends BaseEntity {

    @Column(name = "cuit", nullable = false, unique = true, length = 11)
    private String cuit;

    @Column(name = "nombre", nullable = false, length = 160)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 24)
    private TipoOrganizacion tipo;

    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public TipoOrganizacion getTipo() { return tipo; }
    public void setTipo(TipoOrganizacion tipo) { this.tipo = tipo; }
}
