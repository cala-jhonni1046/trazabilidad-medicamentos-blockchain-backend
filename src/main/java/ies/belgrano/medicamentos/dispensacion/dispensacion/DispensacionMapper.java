package ies.belgrano.medicamentos.operaciones.dispensacion;

import org.springframework.stereotype.Component;

@Component
public class DispensacionMapper {

    public Dispensacion toEntity(DispensacionRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Dispensacion entity = new Dispensacion();
        entity.setRecetaNumero(dto.getRecetaNumero());
        entity.setCodigoQR(dto.getCodigoQR());
        entity.setDniPaciente(dto.getDniPaciente());
        entity.setFarmaciaCuit(dto.getFarmaciaCuit());
        entity.setFechaDispensacion(dto.getFechaDispensacion());
        return entity;
    }

    public DispensacionResponseDTO toResponseDTO(Dispensacion entity) {
        if (entity == null) {
            return null;
        }
        DispensacionResponseDTO dto = new DispensacionResponseDTO();
        dto.setId(entity.getId());
        dto.setRecetaNumero(entity.getRecetaNumero());
        dto.setCodigoQR(entity.getCodigoQR());
        dto.setDniPaciente(entity.getDniPaciente());
        dto.setFarmaciaCuit(entity.getFarmaciaCuit());
        dto.setFechaDispensacion(entity.getFechaDispensacion());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
