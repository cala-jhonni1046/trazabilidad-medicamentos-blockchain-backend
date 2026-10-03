package ies.belgrano.medicamentos.unidadTrazable;

import ies.belgrano.medicamentos.utils.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class UnidadTrazable extends BaseEntity {
	private String codigoUnicoQR;
	private String lotePertenece;
	private String medicamento;
	private String estado;
	private String dniPacienteDispensado;
	public String getCodigoUnicoQR() {
		return codigoUnicoQR;
	}
	public void setCodigoUnicoQR(String codigoUnicoQR) {
		this.codigoUnicoQR = codigoUnicoQR;
	}
	public String getLotePertenece() {
		return lotePertenece;
	}
	public void setLotePertenece(String lotePertenece) {
		this.lotePertenece = lotePertenece;
	}
	public String getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDniPacienteDispensado() {
		return dniPacienteDispensado;
	}
	public void setDniPacienteDispensado(String dniPacienteDispensado) {
		this.dniPacienteDispensado = dniPacienteDispensado;
	}
	
	
	

}
