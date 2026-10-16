package ies.belgrano.medicamentos.unidadTrazable;

import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.lote.Lote;

@Component
public class UnidadTrazableMapper {
    public UnidadTrazable toEntity(UnidadTrazableRequestDTO dto) {
        if (dto == null) return null;
        UnidadTrazable entity = new UnidadTrazable();
        entity.setCodigoUnicoQR(dto.getCodigoUnicoQR());
        entity.setEstado(dto.getEstado());
        if (dto.getLoteId() != null) {
            Lote lote = new Lote();
            lote.setId(dto.getLoteId());
            entity.setLote(lote);
        }
        return entity;
    }

    public UnidadTrazableResponseDTO toResponseDTO(UnidadTrazable entity) {
        if (entity == null) return null;
        UnidadTrazableResponseDTO dto = new UnidadTrazableResponseDTO();
        dto.setId(entity.getId());
        dto.setCodigoUnicoQR(entity.getCodigoUnicoQR());
        dto.setEstado(entity.getEstado());
        if (entity.getLote() != null) {
            dto.setLoteId(entity.getLote().getId());
            dto.setCodigoLote(entity.getLote().getCodigoLote());
        }
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
