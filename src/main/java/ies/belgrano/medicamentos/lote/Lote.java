package ies.belgrano.medicamentos.lote;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Lote {
    @Id	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigoLote;
	private String medicamento;
	private String laboratorioOrigen;
	private String cuitLogistica;
	private String cuitFarmaciaOrigen;
	private String patenteCamion;
	private Double tempMin;
	private Double tempMax;
	private LocalDateTime fechaHoraFabricacion;
	private String estado;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigoLote() {
		return codigoLote;
	}
	public void setCodigoLote(String codigoLote) {
		this.codigoLote = codigoLote;
	}
	public String getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}
	public String getLaboratorioOrigen() {
		return laboratorioOrigen;
	}
	public void setLaboratorioOrigen(String laboratorioOrigen) {
		this.laboratorioOrigen = laboratorioOrigen;
	}
	public String getCuitLogistica() {
		return cuitLogistica;
	}
	public void setCuitLogistica(String cuitLogistica) {
		this.cuitLogistica = cuitLogistica;
	}
	public String getCuitFarmaciaOrigen() {
		return cuitFarmaciaOrigen;
	}
	public void setCuitFarmaciaOrigen(String cuitFarmaciaOrigen) {
		this.cuitFarmaciaOrigen = cuitFarmaciaOrigen;
	}
	public String getPatenteCamion() {
		return patenteCamion;
	}
	public void setPatenteCamion(String patenteCamion) {
		this.patenteCamion = patenteCamion;
	}
	public Double getTempMin() {
		return tempMin;
	}
	public void setTempMin(Double tempMin) {
		this.tempMin = tempMin;
	}
	public Double getTempMax() {
		return tempMax;
	}
	public void setTempMax(Double tempMax) {
		this.tempMax = tempMax;
	}
	public LocalDateTime getFechaHoraFabricacion() {
		return fechaHoraFabricacion;
	}
	public void setFechaHoraFabricacion(LocalDateTime fechaHoraFabricacion) {
		this.fechaHoraFabricacion = fechaHoraFabricacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
