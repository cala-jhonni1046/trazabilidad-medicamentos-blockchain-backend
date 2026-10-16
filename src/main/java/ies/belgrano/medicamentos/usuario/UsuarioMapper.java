package ies.belgrano.medicamentos.usuario;

import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        throw new UnsupportedOperationException("Usuario es una clase abstracta y no puede instanciarse directamente. Utilice la entidad especializada correspondiente.");
    }

    public void copyToEntity(UsuarioRequestDTO dto, Usuario entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setEmail(dto.getEmail());
        entity.setPasswordHash(dto.getPasswordHash());
        entity.setRol(dto.getRol());
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
