package ies.belgrano.medicamentos.usuario;

import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario entity = new Usuario();
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail().trim().toLowerCase());
        }
        entity.setRol(dto.getRol());
        entity.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return entity;
    }

    public void copyToEntity(UsuarioRequestDTO dto, Usuario entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail().trim().toLowerCase());
        }
        if (dto.getRol() != null) {
            entity.setRol(dto.getRol());
        }
        if (dto.getActivo() != null) {
            entity.setActivo(dto.getActivo());
        }
    }

    public UsuarioResponseDTO toResponseDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setRol(entity.getRol());
        dto.setActivo(entity.isActivo());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
