package ies.belgrano.medicamentos.dispensacion;

import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;

@Component
public class DispensacionMapper {
    public Dispensacion toEntity(DispensacionRequestDTO dto) {
        if (dto == null) return null;
        Dispensacion entity = new Dispensacion();
        entity.setRecetaNumero(dto.getRecetaNumero());
        entity.setDniPaciente(dto.getDniPaciente());
        entity.setFechaDispensacion(dto.getFechaDispensacion());
        if (dto.getUnidadTrazableId() != null) {
            UnidadTrazable unidad = new UnidadTrazable(); unidad.setId(dto.getUnidadTrazableId());
            entity.setUnidadTrazable(unidad);
        }
        if (dto.getFarmaciaId() != null) {
            Organizacion farmacia = new Organizacion(); farmacia.setId(dto.getFarmaciaId()); entity.setFarmacia(farmacia);
        }
        return entity;
    }
    public DispensacionResponseDTO toResponseDTO(Dispensacion entity) {
        if (entity == null) return null;
        DispensacionResponseDTO dto = new DispensacionResponseDTO();
        dto.setId(entity.getId()); dto.setRecetaNumero(entity.getRecetaNumero());
        dto.setFechaDispensacion(entity.getFechaDispensacion());
        if (entity.getUnidadTrazable() != null) {
            dto.setUnidadTrazableId(entity.getUnidadTrazable().getId());
            dto.setCodigoUnicoQR(entity.getUnidadTrazable().getCodigoUnicoQR());
        }
        if (entity.getFarmacia() != null) {
            dto.setFarmaciaId(entity.getFarmacia().getId()); dto.setFarmaciaNombre(entity.getFarmacia().getNombre());
            dto.setFarmaciaCuit(entity.getFarmacia().getCuit());
        }
        dto.setFechaCreacion(entity.getFechaCreacion()); dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
