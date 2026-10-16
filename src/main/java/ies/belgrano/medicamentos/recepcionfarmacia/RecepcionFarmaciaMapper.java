package ies.belgrano.medicamentos.recepcionfarmacia;

import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.organizacion.Organizacion;

@Component
public class RecepcionFarmaciaMapper {
    public RecepcionFarmacia toEntity(RecepcionFarmaciaRequestDTO dto) {
        if (dto == null) return null;
        RecepcionFarmacia entity = new RecepcionFarmacia();
        entity.setNumeroComprobante(dto.getNumeroComprobante());
        entity.setFechaHoraRecepcion(dto.getFechaHoraRecepcion());
        entity.setFarmaceuticoResponsable(dto.getFarmaceuticoResponsable());
        entity.setEstadoConformidad(dto.getEstadoConformidad());
        entity.setCadenaFrioIntacta(dto.getCadenaFrioIntacta());
        entity.setObservaciones(dto.getObservaciones());
        if (dto.getFarmaciaId() != null) {
            Organizacion farmacia = new Organizacion(); farmacia.setId(dto.getFarmaciaId()); entity.setFarmacia(farmacia);
        }
        if (dto.getDespachoLogisticoId() != null) {
            DespachoLogistico despacho = new DespachoLogistico(); despacho.setId(dto.getDespachoLogisticoId());
            entity.setDespachoLogistico(despacho);
        }
        return entity;
    }
    public RecepcionFarmaciaResponseDTO toResponseDTO(RecepcionFarmacia entity) {
        if (entity == null) return null;
        RecepcionFarmaciaResponseDTO dto = new RecepcionFarmaciaResponseDTO();
        dto.setId(entity.getId()); dto.setNumeroComprobante(entity.getNumeroComprobante());
        dto.setFechaHoraRecepcion(entity.getFechaHoraRecepcion());
        dto.setFarmaceuticoResponsable(entity.getFarmaceuticoResponsable());
        dto.setEstadoConformidad(entity.getEstadoConformidad()); dto.setCadenaFrioIntacta(entity.getCadenaFrioIntacta());
        dto.setObservaciones(entity.getObservaciones());
        if (entity.getFarmacia() != null) {
            dto.setFarmaciaId(entity.getFarmacia().getId()); dto.setFarmaciaNombre(entity.getFarmacia().getNombre());
            dto.setFarmaciaCuit(entity.getFarmacia().getCuit());
        }
        if (entity.getDespachoLogistico() != null) {
            dto.setDespachoLogisticoId(entity.getDespachoLogistico().getId());
            dto.setNumeroRemito(entity.getDespachoLogistico().getNumeroRemito());
        }
        dto.setFechaCreacion(entity.getFechaCreacion()); dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
