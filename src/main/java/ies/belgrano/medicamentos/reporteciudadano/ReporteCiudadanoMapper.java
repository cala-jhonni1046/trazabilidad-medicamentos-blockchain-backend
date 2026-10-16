package ies.belgrano.medicamentos.reporteciudadano;

import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;

@Component
public class ReporteCiudadanoMapper {
    public ReporteCiudadano toEntity(ReporteCiudadanoRequestDTO dto) {
        if (dto == null) return null;
        ReporteCiudadano entity = new ReporteCiudadano();
        entity.setMotivoReporte(dto.getMotivoReporte());
        entity.setFechaHoraReporte(dto.getFechaHoraReporte());
        entity.setEstado(dto.getEstado());
        if (dto.getUnidadTrazableId() != null) {
            UnidadTrazable unidad = new UnidadTrazable(); unidad.setId(dto.getUnidadTrazableId());
            entity.setUnidadTrazable(unidad);
        }
        if (dto.getInspectorAsignadoId() != null) {
            InspectorANMAT inspector = new InspectorANMAT(); inspector.setId(dto.getInspectorAsignadoId());
            entity.setInspector(inspector);
        }
        return entity;
    }
    public ReporteCiudadanoResponseDTO toResponseDTO(ReporteCiudadano entity) {
        if (entity == null) return null;
        ReporteCiudadanoResponseDTO dto = new ReporteCiudadanoResponseDTO();
        dto.setId(entity.getId()); dto.setMotivoReporte(entity.getMotivoReporte());
        dto.setFechaHoraReporte(entity.getFechaHoraReporte()); dto.setEstado(entity.getEstado());
        if (entity.getUnidadTrazable() != null) {
            dto.setUnidadTrazableId(entity.getUnidadTrazable().getId());
            dto.setCodigoUnicoQR(entity.getUnidadTrazable().getCodigoUnicoQR());
        }
        if (entity.getInspector() != null) {
            dto.setInspectorAsignadoId(entity.getInspector().getId());
            dto.setInspectorNombre(entity.getInspector().getNombreCompleto());
        }
        dto.setFechaCreacion(entity.getFechaCreacion()); dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
