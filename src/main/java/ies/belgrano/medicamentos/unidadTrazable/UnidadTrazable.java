package ies.belgrano.medicamentos.unidadTrazable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UnidadTrazable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigoUnicoQR;
	private String lotePertenece;
	private String medicamento;
	private String estado;
	private String dniPacienteDispensado;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
