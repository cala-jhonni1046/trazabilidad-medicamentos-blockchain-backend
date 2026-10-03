package ies.belgrano.medicamentos.lote;

import java.time.LocalDateTime;

import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Entity;
@Entity
public class Lote extends BaseEntity {
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
