package ies.belgrano.medicamentos.unidadTrazable;

import org.springframework.stereotype.Component;

@Component
public class UnidadTrazableMapper {

    public UnidadTrazable toEntity(UnidadTrazableRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        UnidadTrazable entity = new UnidadTrazable();
        entity.setCodigoUnicoQR(dto.getCodigoUnicoQR());
        entity.setLotePertenece(dto.getLotePertenece());
        entity.setMedicamento(dto.getMedicamento());
        entity.setEstado(dto.getEstado());
        entity.setDniPacienteDispensado(dto.getDniPacienteDispensado());
        return entity;
    }

    public UnidadTrazableResponseDTO toResponseDTO(UnidadTrazable entity) {
        if (entity == null) {
            return null;
        }
        UnidadTrazableResponseDTO dto = new UnidadTrazableResponseDTO();
        dto.setId(entity.getId());
        dto.setCodigoUnicoQR(entity.getCodigoUnicoQR());
        dto.setLotePertenece(entity.getLotePertenece());
        dto.setMedicamento(entity.getMedicamento());
        dto.setEstado(entity.getEstado());
        dto.setDniPacienteDispensado(entity.getDniPacienteDispensado());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
