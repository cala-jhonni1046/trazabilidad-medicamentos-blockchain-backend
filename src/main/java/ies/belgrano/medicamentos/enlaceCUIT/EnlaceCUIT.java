package ies.belgrano.medicamentos.enlaceCUIT;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class EnlaceCUIT extends BaseEntity {
	
	private String nombreLaboratorio;
	private String cuitLaboratorio;
	private String cuitLogistica;
	private String cuitFarmacia;
	private String numeroDisposicionANMAT;
	private String documentoCertificadoUrl;
	private String estado;
	private LocalDateTime fechaSolicitud;
	private LocalDateTime fechaAprobacionANMAT;
	
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

}
