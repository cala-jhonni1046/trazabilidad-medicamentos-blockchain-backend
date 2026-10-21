package ies.belgrano.medicamentos.dispensacion;

import java.time.LocalDateTime;

public class DispensacionResponseDTO {
    private Long id;
    private String recetaNumero;
    private Long unidadTrazableId;
    private String codigoUnicoQR;
    private Long farmaciaId;
    private String farmaciaNombre;
    private String farmaciaCuit;
    private LocalDateTime fechaDispensacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }
    public String getRecetaNumero() { return recetaNumero; }
    public void setRecetaNumero(String value) { this.recetaNumero = value; }
    public Long getUnidadTrazableId() { return unidadTrazableId; }
    public void setUnidadTrazableId(Long value) { this.unidadTrazableId = value; }
    public String getCodigoUnicoQR() { return codigoUnicoQR; }
    public void setCodigoUnicoQR(String value) { this.codigoUnicoQR = value; }
    public String getCodigoQr() { return codigoUnicoQR; }
    public void setCodigoQr(String value) { this.codigoUnicoQR = value; }
    public Long getFarmaciaId() { return farmaciaId; }
    public void setFarmaciaId(Long value) { this.farmaciaId = value; }
    public String getFarmaciaNombre() { return farmaciaNombre; }
    public void setFarmaciaNombre(String value) { this.farmaciaNombre = value; }
    public String getFarmaciaCuit() { return farmaciaCuit; }
    public void setFarmaciaCuit(String value) { this.farmaciaCuit = value; }
    public LocalDateTime getFechaDispensacion() { return fechaDispensacion; }
    public void setFechaDispensacion(LocalDateTime value) { this.fechaDispensacion = value; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime value) { this.fechaCreacion = value; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime value) { this.fechaActualizacion = value; }
}
