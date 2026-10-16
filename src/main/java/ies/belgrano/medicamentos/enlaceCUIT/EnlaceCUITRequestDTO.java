package ies.belgrano.medicamentos.enlaceCUIT;

import java.time.LocalDateTime;

public class EnlaceCUITRequestDTO {

    private String nombreLaboratorio;
    private String cuitLaboratorio;
    private String cuitLogistica;
    private String cuitFarmacia;
    private String numeroDisposicionANMAT;
    private String documentoCertificadoUrl;
    private EstadoEnlaceCuit estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaAprobacionANMAT;

    public EnlaceCUITRequestDTO() {
    }

    public EnlaceCUITRequestDTO(String nombreLaboratorio, String cuitLaboratorio,
                               String cuitLogistica, String cuitFarmacia,
                               String numeroDisposicionANMAT, String documentoCertificadoUrl,
                               EstadoEnlaceCuit estado, LocalDateTime fechaSolicitud,
                               LocalDateTime fechaAprobacionANMAT) {
        this.nombreLaboratorio = nombreLaboratorio;
        this.cuitLaboratorio = cuitLaboratorio;
        this.cuitLogistica = cuitLogistica;
        this.cuitFarmacia = cuitFarmacia;
        this.numeroDisposicionANMAT = numeroDisposicionANMAT;
        this.documentoCertificadoUrl = documentoCertificadoUrl;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAprobacionANMAT = fechaAprobacionANMAT;
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

    public EstadoEnlaceCuit getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnlaceCuit estado) {
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
}
