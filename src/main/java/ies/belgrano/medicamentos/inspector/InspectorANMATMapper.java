package ies.belgrano.medicamentos.inspector;

import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.usuario.Usuario;

@Component
public class InspectorANMATMapper {

    public InspectorANMAT toEntity(InspectorANMATRequestDTO dto, Usuario usuario) {
        if (dto == null) {
            return null;
        }
        InspectorANMAT entity = new InspectorANMAT();
        entity.setUsuario(usuario);
        copyFields(dto, entity);
        return entity;
    }

    public void copyToEntity(InspectorANMATRequestDTO dto, InspectorANMAT entity, Usuario usuario) {
        if (dto == null || entity == null) {
            return;
        }
        if (usuario != null) {
            entity.setUsuario(usuario);
        }
        copyFields(dto, entity);
    }

    private void copyFields(InspectorANMATRequestDTO dto, InspectorANMAT entity) {
        if (dto.getDni() != null) {
            entity.setDni(dto.getDni().trim());
        }
        if (dto.getLegajoOficial() != null) {
            entity.setLegajoOficial(dto.getLegajoOficial().trim());
        }
        if (dto.getNombreCompleto() != null) {
            entity.setNombreCompleto(dto.getNombreCompleto().trim());
        }
        if (dto.getEmailInstitucional() != null) {
            entity.setEmailInstitucional(dto.getEmailInstitucional().trim().toLowerCase());
        }
        if (dto.getFechaHoraAlta() != null) {
            entity.setFechaHoraAlta(dto.getFechaHoraAlta());
        }
        if (dto.getJefeAutorizador() != null) {
            entity.setJefeAutorizador(dto.getJefeAutorizador().trim());
        }
        if (dto.getEstado() != null) {
            entity.setEstado(dto.getEstado());
        }
    }

    public InspectorANMATResponseDTO toResponseDTO(InspectorANMAT entity) {
        if (entity == null) {
            return null;
        }
        InspectorANMATResponseDTO dto = new InspectorANMATResponseDTO();
        dto.setId(entity.getId());
        if (entity.getUsuario() != null) {
            dto.setUsuarioId(entity.getUsuario().getId());
            dto.setUsuarioEmail(entity.getUsuario().getEmail());
            dto.setUsuarioRol(entity.getUsuario().getRol());
        }
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
