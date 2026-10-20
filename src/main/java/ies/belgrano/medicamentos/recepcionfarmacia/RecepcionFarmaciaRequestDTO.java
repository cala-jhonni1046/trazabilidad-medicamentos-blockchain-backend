package ies.belgrano.medicamentos.recepcionfarmacia;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RecepcionFarmaciaRequestDTO {

    @NotBlank(message = "El número de comprobante es obligatorio.")
    @Size(max = 80, message = "El número de comprobante no puede superar los 80 caracteres.")
    private String numeroComprobante;

    private LocalDateTime fechaHoraRecepcion;

    @NotNull(message = "El ID de farmacia es obligatorio.")
    private Long farmaciaId;

    @Size(max = 160, message = "El farmacéutico responsable no puede superar los 160 caracteres.")
    private String farmaceuticoResponsable;

    @NotNull(message = "El ID de despacho logístico es obligatorio.")
    @JsonAlias({"despachoId"})
    private Long despachoLogisticoId;

    @NotNull(message = "El estado de conformidad es obligatorio.")
    private EstadoRecepcion estadoConformidad;

    @NotNull(message = "Debe indicar si la cadena de frío se encuentra intacta.")
    private Boolean cadenaFrioIntacta;

    @Size(max = 1000, message = "Las observaciones no pueden superar los 1000 caracteres.")
    private String observaciones;

    public RecepcionFarmaciaRequestDTO() {
    }

    public RecepcionFarmaciaRequestDTO(String numeroComprobante, LocalDateTime fechaHoraRecepcion,
                                      Long farmaciaId, String farmaceuticoResponsable,
                                      Long despachoLogisticoId, EstadoRecepcion estadoConformidad,
                                      Boolean cadenaFrioIntacta, String observaciones) {
        this.numeroComprobante = numeroComprobante;
        this.fechaHoraRecepcion = fechaHoraRecepcion;
        this.farmaciaId = farmaciaId;
        this.farmaceuticoResponsable = farmaceuticoResponsable;
        this.despachoLogisticoId = despachoLogisticoId;
        this.estadoConformidad = estadoConformidad;
        this.cadenaFrioIntacta = cadenaFrioIntacta;
        this.observaciones = observaciones;
    }

    public String getNumeroComprobante() { return numeroComprobante; }
    public void setNumeroComprobante(String value) { this.numeroComprobante = value; }

    public LocalDateTime getFechaHoraRecepcion() { return fechaHoraRecepcion; }
    public void setFechaHoraRecepcion(LocalDateTime value) { this.fechaHoraRecepcion = value; }

    public Long getFarmaciaId() { return farmaciaId; }
    public void setFarmaciaId(Long value) { this.farmaciaId = value; }

    public String getFarmaceuticoResponsable() { return farmaceuticoResponsable; }
    public void setFarmaceuticoResponsable(String value) { this.farmaceuticoResponsable = value; }

    public Long getDespachoLogisticoId() { return despachoLogisticoId; }
    public void setDespachoLogisticoId(Long value) { this.despachoLogisticoId = value; }

    public void setDespachoId(Long despachoId) {
        if (this.despachoLogisticoId == null) {
            this.despachoLogisticoId = despachoId;
        }
    }

    public EstadoRecepcion getEstadoConformidad() { return estadoConformidad; }
    public void setEstadoConformidad(EstadoRecepcion value) { this.estadoConformidad = value; }

    public Boolean getCadenaFrioIntacta() { return cadenaFrioIntacta; }
    public void setCadenaFrioIntacta(Boolean value) { this.cadenaFrioIntacta = value; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }
}
