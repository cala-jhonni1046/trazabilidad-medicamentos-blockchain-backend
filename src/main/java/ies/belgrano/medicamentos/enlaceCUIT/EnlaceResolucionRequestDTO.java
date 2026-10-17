package ies.belgrano.medicamentos.enlaceCUIT;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EnlaceResolucionRequestDTO {

    @NotNull(message = "El ID del inspector es obligatorio")
    private Long inspectorId;

    @Size(max = 80, message = "El número de disposición ANMAT no puede superar los 80 caracteres")
    private String numeroDisposicionANMAT;

    @Size(max = 500, message = "La URL del certificado no puede superar los 500 caracteres")
    private String documentoCertificadoUrl;

    public EnlaceResolucionRequestDTO() {
    }

    public EnlaceResolucionRequestDTO(Long inspectorId, String numeroDisposicionANMAT, String documentoCertificadoUrl) {
        this.inspectorId = inspectorId;
        this.numeroDisposicionANMAT = numeroDisposicionANMAT;
        this.documentoCertificadoUrl = documentoCertificadoUrl;
    }

    public Long getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Long inspectorId) {
        this.inspectorId = inspectorId;
    }

    public Long getInspectorAprobadorId() {
        return inspectorId;
    }

    public void setInspectorAprobadorId(Long inspectorAprobadorId) {
        if (this.inspectorId == null) {
            this.inspectorId = inspectorAprobadorId;
        }
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
}
