package ies.belgrano.medicamentos.cuarentena;

import org.springframework.stereotype.Component;

@Component
public class CuarentenaMapper {

    public Cuarentena toEntity(CuarentenaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Cuarentena entity = new Cuarentena();
        entity.setCodigoCuarentena(dto.getCodigoCuarentena());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setMotivo(dto.getMotivo());
        entity.setEstado(dto.getEstado());
        entity.setResponsable(dto.getResponsable());
        entity.setUbicacionFisica(dto.getUbicacionFisica());
        entity.setLoteId(dto.getLoteId());
        entity.setUnidadTrazableId(dto.getUnidadTrazableId());
        entity.setResolucionFinal(dto.getResolucionFinal());
        entity.setObservaciones(dto.getObservaciones());
        return entity;
    }

    public CuarentenaResponseDTO toResponseDTO(Cuarentena entity) {
        if (entity == null) {
            return null;
        }
        CuarentenaResponseDTO dto = new CuarentenaResponseDTO();
        dto.setId(entity.getId());
        dto.setCodigoCuarentena(entity.getCodigoCuarentena());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setMotivo(entity.getMotivo());
        dto.setEstado(entity.getEstado());
        dto.setResponsable(entity.getResponsable());
        dto.setUbicacionFisica(entity.getUbicacionFisica());
        dto.setLoteId(entity.getLoteId());
        dto.setUnidadTrazableId(entity.getUnidadTrazableId());
        dto.setResolucionFinal(entity.getResolucionFinal());
        dto.setObservaciones(entity.getObservaciones());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
