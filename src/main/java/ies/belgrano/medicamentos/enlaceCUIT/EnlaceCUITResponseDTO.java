package ies.belgrano.medicamentos.enlaceCUIT;

import java.time.LocalDateTime;

public class EnlaceCUITResponseDTO {

    private Long id;
    private String nombreLaboratorio;
    private String cuitLaboratorio;
    private String cuitLogistica;
    private String cuitFarmacia;
    private String numeroDisposicionANMAT;
    private String documentoCertificadoUrl;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaAprobacionANMAT;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public EnlaceCUITResponseDTO() {
    }

    public EnlaceCUITResponseDTO(Long id, String nombreLaboratorio, String cuitLaboratorio,
                                String cuitLogistica, String cuitFarmacia,
                                String numeroDisposicionANMAT, String documentoCertificadoUrl,
                                String estado, LocalDateTime fechaSolicitud,
                                LocalDateTime fechaAprobacionANMAT,
                                LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.nombreLaboratorio = nombreLaboratorio;
        this.cuitLaboratorio = cuitLaboratorio;
        this.cuitLogistica = cuitLogistica;
        this.cuitFarmacia = cuitFarmacia;
        this.numeroDisposicionANMAT = numeroDisposicionANMAT;
        this.documentoCertificadoUrl = documentoCertificadoUrl;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAprobacionANMAT = fechaAprobacionANMAT;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreLaboratorio() {
        return nombreLaboratorio;
    }

    public void setNombreLaboratorio(String nombreLaboratorio) {
        this.nombreLaboratorio = nombreLaboratorio;
    }

    public String getCuitLaboratorio() {
        return cuitLaboratorio;
    }

    public void setCuitLaboratorio(String cuitLaboratorio) {
        this.cuitLaboratorio = cuitLaboratorio;
    }

    public String getCuitLogistica() {
        return cuitLogistica;
    }

    public void setCuitLogistica(String cuitLogistica) {
        this.cuitLogistica = cuitLogistica;
    }

    public String getCuitFarmacia() {
        return cuitFarmacia;
    }

    public void setCuitFarmacia(String cuitFarmacia) {
        this.cuitFarmacia = cuitFarmacia;
    }

    public String getNumeroDisposicionANMAT() {
        return numeroDisposicionANMAT;
    }

    public void setNumeroDisposicionANMAT(String numeroDisposicionANMAT) {
        this.numeroDisposicionANMAT = numeroDisposicionANMAT;
    }

    public String getDocumentoCertificadoUrl() {
        return documentoCertificadoUrl;
    }

    public void setDocumentoCertificadoUrl(String documentoCertificadoUrl) {
        this.documentoCertificadoUrl = documentoCertificadoUrl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaAprobacionANMAT() {
        return fechaAprobacionANMAT;
    }

    public void setFechaAprobacionANMAT(LocalDateTime fechaAprobacionANMAT) {
        this.fechaAprobacionANMAT = fechaAprobacionANMAT;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
