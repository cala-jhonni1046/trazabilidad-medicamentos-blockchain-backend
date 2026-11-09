package ies.belgrano.medicamentos.auditoria;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "organizacion_id")
    private Long organizacionId;

    @Column(name = "metodo", length = 10)
    private String metodo;

    @Column(name = "ruta", length = 500)
    private String ruta;

    @Column(name = "recurso", length = 120)
    private String recurso;

    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Column(name = "resultado", length = 50)
    private String resultado;

    public Auditoria() {
    }

    public Auditoria(Long usuarioId, Long organizacionId, String metodo, String ruta,
                     String recurso, Instant fecha, String resultado) {
        this.usuarioId = usuarioId;
        this.organizacionId = organizacionId;
        this.metodo = metodo;
        this.ruta = ruta;
        this.recurso = recurso;
        this.fecha = fecha;
        this.resultado = resultado;
    }

    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getOrganizacionId() { return organizacionId; }
    public void setOrganizacionId(Long organizacionId) { this.organizacionId = organizacionId; }
    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
    public String getRecurso() { return recurso; }
    public void setRecurso(String recurso) { this.recurso = recurso; }
    public Instant getFecha() { return fecha; }
    public void setFecha(Instant fecha) { this.fecha = fecha; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
}

