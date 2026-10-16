package ies.belgrano.medicamentos.cuarentena;

import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.lote.Lote;

@Component
public class CuarentenaMapper {
    public Cuarentena toEntity(CuarentenaRequestDTO dto) {
        if (dto == null) return null;
        Cuarentena entity = new Cuarentena();
        entity.setCodigoCuarentena(dto.getCodigoCuarentena());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setMotivo(dto.getMotivo());
        entity.setEstado(dto.getEstado());
        entity.setResolucionFinal(dto.getResolucionFinal());
        entity.setObservaciones(dto.getObservaciones());
        if (dto.getLoteId() != null) {
            Lote lote = new Lote(); lote.setId(dto.getLoteId()); entity.setLote(lote);
        }
        if (dto.getInspectorResponsableId() != null) {
            InspectorANMAT inspector = new InspectorANMAT(); inspector.setId(dto.getInspectorResponsableId());
            entity.setInspectorResponsable(inspector);
        }
        return entity;
    }
    public CuarentenaResponseDTO toResponseDTO(Cuarentena entity) {
        if (entity == null) return null;
        CuarentenaResponseDTO dto = new CuarentenaResponseDTO();
        dto.setId(entity.getId()); dto.setCodigoCuarentena(entity.getCodigoCuarentena());
        dto.setFechaInicio(entity.getFechaInicio()); dto.setFechaFin(entity.getFechaFin());
        dto.setMotivo(entity.getMotivo()); dto.setEstado(entity.getEstado());
        dto.setResolucionFinal(entity.getResolucionFinal()); dto.setObservaciones(entity.getObservaciones());
        if (entity.getLote() != null) {
            dto.setLoteId(entity.getLote().getId()); dto.setCodigoLote(entity.getLote().getCodigoLote());
        }
        if (entity.getInspectorResponsable() != null) {
            dto.setInspectorResponsableId(entity.getInspectorResponsable().getId());
            dto.setInspectorNombre(entity.getInspectorResponsable().getNombreCompleto());
        }
        dto.setFechaCreacion(entity.getFechaCreacion()); dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
