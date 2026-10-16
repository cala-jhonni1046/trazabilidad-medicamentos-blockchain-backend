package ies.belgrano.medicamentos.unidadTrazable;

import java.time.LocalDateTime;

public class UnidadTrazableResponseDTO {

    private Long id;
    private String codigoUnicoQR;
    private String lotePertenece;
    private String medicamento;
    private String estado;
    private String dniPacienteDispensado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public UnidadTrazableResponseDTO() {
    }

    public UnidadTrazableResponseDTO(Long id, String codigoUnicoQR, String lotePertenece,
                                     String medicamento, String estado, String dniPacienteDispensado,
                                     LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.codigoUnicoQR = codigoUnicoQR;
        this.lotePertenece = lotePertenece;
        this.medicamento = medicamento;
        this.estado = estado;
        this.dniPacienteDispensado = dniPacienteDispensado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

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
