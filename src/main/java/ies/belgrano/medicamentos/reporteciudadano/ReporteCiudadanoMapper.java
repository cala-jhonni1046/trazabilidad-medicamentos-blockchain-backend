package ies.belgrano.medicamentos.reporteciudadano;

import org.springframework.stereotype.Component;

@Component
public class ReporteCiudadanoMapper {

    public ReporteCiudadano toEntity(ReporteCiudadanoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ReporteCiudadano entity = new ReporteCiudadano();
        entity.setCodigoQRAfectado(dto.getCodigoQRAfectado());
        entity.setInspectorAsignado(dto.getInspectorAsignado());
        entity.setMotivoReporte(dto.getMotivoReporte());
        entity.setFechaHoraReporte(dto.getFechaHoraReporte());
        entity.setEstadoAuditoria(dto.getEstadoAuditoria());
        return entity;
    }

    public ReporteCiudadanoResponseDTO toResponseDTO(ReporteCiudadano entity) {
        if (entity == null) {
            return null;
        }
        ReporteCiudadanoResponseDTO dto = new ReporteCiudadanoResponseDTO();
        dto.setId(entity.getId());
        dto.setCodigoQRAfectado(entity.getCodigoQRAfectado());
        dto.setInspectorAsignado(entity.getInspectorAsignado());
        dto.setMotivoReporte(entity.getMotivoReporte());
        dto.setFechaHoraReporte(entity.getFechaHoraReporte());
        dto.setEstadoAuditoria(entity.getEstadoAuditoria());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
