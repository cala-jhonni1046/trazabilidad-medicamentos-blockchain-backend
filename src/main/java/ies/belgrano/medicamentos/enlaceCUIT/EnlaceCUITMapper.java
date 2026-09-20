package ies.belgrano.medicamentos.enlaceCUIT;

import org.springframework.stereotype.Component;

@Component
public class EnlaceCUITMapper {

    public EnlaceCUIT toEntity(EnlaceCUITRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        EnlaceCUIT entity = new EnlaceCUIT();
        entity.setNombreLaboratorio(dto.getNombreLaboratorio());
        entity.setCuitLaboratorio(dto.getCuitLaboratorio());
        entity.setCuitLogistica(dto.getCuitLogistica());
        entity.setCuitFarmacia(dto.getCuitFarmacia());
        entity.setNumeroDisposicionANMAT(dto.getNumeroDisposicionANMAT());
        entity.setDocumentoCertificadoUrl(dto.getDocumentoCertificadoUrl());
        entity.setEstado(dto.getEstado());
        entity.setFechaSolicitud(dto.getFechaSolicitud());
        entity.setFechaAprobacionANMAT(dto.getFechaAprobacionANMAT());
        return entity;
    }

    public EnlaceCUITResponseDTO toResponseDTO(EnlaceCUIT entity) {
        if (entity == null) {
            return null;
        }
        EnlaceCUITResponseDTO dto = new EnlaceCUITResponseDTO();
        dto.setId(entity.getId());
        dto.setNombreLaboratorio(entity.getNombreLaboratorio());
        dto.setCuitLaboratorio(entity.getCuitLaboratorio());
        dto.setCuitLogistica(entity.getCuitLogistica());
        dto.setCuitFarmacia(entity.getCuitFarmacia());
        dto.setNumeroDisposicionANMAT(entity.getNumeroDisposicionANMAT());
        dto.setDocumentoCertificadoUrl(entity.getDocumentoCertificadoUrl());
        dto.setEstado(entity.getEstado());
        dto.setFechaSolicitud(entity.getFechaSolicitud());
        dto.setFechaAprobacionANMAT(entity.getFechaAprobacionANMAT());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
