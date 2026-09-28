package ies.belgrano.medicamentos.recepcionfarmacia;

import org.springframework.stereotype.Component;

@Component
public class RecepcionFarmaciaMapper {

    public RecepcionFarmacia toEntity(RecepcionFarmaciaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        RecepcionFarmacia entity = new RecepcionFarmacia();
        entity.setNumeroComprobante(dto.getNumeroComprobante());
        entity.setFechaHoraRecepcion(dto.getFechaHoraRecepcion());
        entity.setFarmaciaCuit(dto.getFarmaciaCuit());
        entity.setFarmaciaNombre(dto.getFarmaciaNombre());
        entity.setFarmaceuticoResponsable(dto.getFarmaceuticoResponsable());
        entity.setDespachoLogisticoId(dto.getDespachoLogisticoId());
        entity.setEstadoConformidad(dto.getEstadoConformidad());
        entity.setCadenaFrioIntacta(dto.getCadenaFrioIntacta());
        entity.setObservaciones(dto.getObservaciones());
        return entity;
    }

    public RecepcionFarmaciaResponseDTO toResponseDTO(RecepcionFarmacia entity) {
        if (entity == null) {
            return null;
        }
        RecepcionFarmaciaResponseDTO dto = new RecepcionFarmaciaResponseDTO();
        dto.setId(entity.getId());
        dto.setNumeroComprobante(entity.getNumeroComprobante());
        dto.setFechaHoraRecepcion(entity.getFechaHoraRecepcion());
        dto.setFarmaciaCuit(entity.getFarmaciaCuit());
        dto.setFarmaciaNombre(entity.getFarmaciaNombre());
        dto.setFarmaceuticoResponsable(entity.getFarmaceuticoResponsable());
        dto.setDespachoLogisticoId(entity.getDespachoLogisticoId());
        dto.setEstadoConformidad(entity.getEstadoConformidad());
        dto.setCadenaFrioIntacta(entity.getCadenaFrioIntacta());
        dto.setObservaciones(entity.getObservaciones());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
