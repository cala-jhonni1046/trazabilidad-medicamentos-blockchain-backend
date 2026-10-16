package ies.belgrano.medicamentos.lote;

import java.time.LocalDateTime;

public class LoteResponseDTO {

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
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public LoteResponseDTO() {
    }

    public LoteResponseDTO(Long id, String codigoLote, String medicamento, String laboratorioOrigen,
                           String cuitLogistica, String cuitFarmaciaOrigen, String patenteCamion,
                           Double tempMin, Double tempMax, LocalDateTime fechaHoraFabricacion,
                           String estado, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.codigoLote = codigoLote;
        this.medicamento = medicamento;
        this.laboratorioOrigen = laboratorioOrigen;
        this.cuitLogistica = cuitLogistica;
        this.cuitFarmaciaOrigen = cuitFarmaciaOrigen;
        this.patenteCamion = patenteCamion;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.fechaHoraFabricacion = fechaHoraFabricacion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

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
