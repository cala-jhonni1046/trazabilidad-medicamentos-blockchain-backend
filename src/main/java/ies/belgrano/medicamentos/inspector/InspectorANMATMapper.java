package ies.belgrano.medicamentos.inspector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.usuario.Usuario;
import ies.belgrano.medicamentos.usuario.UsuarioRepository;

@Component
public class InspectorANMATMapper {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public InspectorANMAT toEntity(InspectorANMATRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        InspectorANMAT entity = new InspectorANMAT();
        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
            entity.setUsuario(usuario);
        }
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
