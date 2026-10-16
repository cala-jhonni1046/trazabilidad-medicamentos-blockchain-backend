package ies.belgrano.medicamentos.enlaceCUIT;

import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.inspector.InspectorANMAT;

@Component
public class EnlaceCUITMapper {
    public EnlaceCUIT toEntity(EnlaceCUITRequestDTO dto) {
        if (dto == null) return null;
        EnlaceCUIT entity = new EnlaceCUIT();
        entity.setLaboratorio(reference(dto.getLaboratorioId()));
        entity.setDistribuidor(reference(dto.getDistribuidorId()));
        entity.setFarmacia(reference(dto.getFarmaciaId()));
        if (dto.getInspectorAprobadorId() != null) {
            InspectorANMAT inspector = new InspectorANMAT(); inspector.setId(dto.getInspectorAprobadorId());
            entity.setInspectorAprobador(inspector);
        }
        entity.setNumeroDisposicionANMAT(dto.getNumeroDisposicionANMAT());
        entity.setDocumentoCertificadoUrl(dto.getDocumentoCertificadoUrl());
        entity.setEstado(dto.getEstado());
        entity.setFechaSolicitud(dto.getFechaSolicitud());
        entity.setFechaAprobacionANMAT(dto.getFechaAprobacionANMAT());
        return entity;
    }
    public EnlaceCUITResponseDTO toResponseDTO(EnlaceCUIT entity) {
        if (entity == null) return null;
        EnlaceCUITResponseDTO dto = new EnlaceCUITResponseDTO();
        dto.setId(entity.getId());
        setOrganization(entity.getLaboratorio(), dto::setLaboratorioId, dto::setLaboratorioNombre, dto::setLaboratorioCuit);
        setOrganization(entity.getDistribuidor(), dto::setDistribuidorId, dto::setDistribuidorNombre, dto::setDistribuidorCuit);
        setOrganization(entity.getFarmacia(), dto::setFarmaciaId, dto::setFarmaciaNombre, dto::setFarmaciaCuit);
        if (entity.getInspectorAprobador() != null) {
            dto.setInspectorAprobadorId(entity.getInspectorAprobador().getId());
            dto.setInspectorAprobadorNombre(entity.getInspectorAprobador().getNombreCompleto());
        }
        dto.setNumeroDisposicionANMAT(entity.getNumeroDisposicionANMAT());
        dto.setDocumentoCertificadoUrl(entity.getDocumentoCertificadoUrl());
        dto.setEstado(entity.getEstado());
        dto.setFechaSolicitud(entity.getFechaSolicitud());
        dto.setFechaAprobacionANMAT(entity.getFechaAprobacionANMAT());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
    private Organizacion reference(Long id) {
        if (id == null) return null;
        Organizacion organizacion = new Organizacion();
        organizacion.setId(id);
        return organizacion;
    }
    private void setOrganization(Organizacion source, java.util.function.Consumer<Long> id,
            java.util.function.Consumer<String> name, java.util.function.Consumer<String> cuit) {
        if (source == null) return;
        id.accept(source.getId()); name.accept(source.getNombre()); cuit.accept(source.getCuit());
    }
}
