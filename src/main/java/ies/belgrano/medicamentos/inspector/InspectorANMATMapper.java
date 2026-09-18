package ies.belgrano.medicamentos.inspector;

import org.springframework.stereotype.Component;

@Component
public class InspectorANMATMapper {

    public InspectorANMAT toEntity(InspectorANMATRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        InspectorANMAT entity = new InspectorANMAT();
        entity.setDni(dto.getDni());
        entity.setLegajoOficial(dto.getLegajoOficial());
        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setEmailInstitucional(dto.getEmailInstitucional());
        entity.setFechaHoraAlta(dto.getFechaHoraAlta());
        entity.setJefeAutorizador(dto.getJefeAutorizador());
        entity.setEstado(dto.getEstado());
        return entity;
    }

    public InspectorANMATResponseDTO toResponseDTO(InspectorANMAT entity) {
        if (entity == null) {
            return null;
        }
        InspectorANMATResponseDTO dto = new InspectorANMATResponseDTO();
        dto.setId(entity.getId());
        dto.setDni(entity.getDni());
        dto.setLegajoOficial(entity.getLegajoOficial());
        dto.setNombreCompleto(entity.getNombreCompleto());
        dto.setEmailInstitucional(entity.getEmailInstitucional());
        dto.setFechaHoraAlta(entity.getFechaHoraAlta());
        dto.setJefeAutorizador(entity.getJefeAutorizador());
        dto.setEstado(entity.getEstado());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
