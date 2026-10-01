package ies.belgrano.medicamentos.unidadTrazable;

public class UnidadTrazableRequestDTO {

    private String codigoUnicoQR;
    private String lotePertenece;
    private String medicamento;
    private String estado;
    private String dniPacienteDispensado;

    public UnidadTrazableRequestDTO() {
    }

    public UnidadTrazableRequestDTO(String codigoUnicoQR, String lotePertenece, String medicamento,
                                    String estado, String dniPacienteDispensado) {
        this.codigoUnicoQR = codigoUnicoQR;
        this.lotePertenece = lotePertenece;
        this.medicamento = medicamento;
        this.estado = estado;
        this.dniPacienteDispensado = dniPacienteDispensado;
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
