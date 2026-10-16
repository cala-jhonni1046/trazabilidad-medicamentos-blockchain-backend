package ies.belgrano.medicamentos.medicamento;

import org.springframework.stereotype.Component;

@Component
public class MedicamentoMapper {

    public Medicamento toEntity(MedicamentoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Medicamento entity = new Medicamento();
        entity.setNombre(dto.getNombre());
        entity.setPrincipioActivo(dto.getPrincipioActivo());
        if (dto.getRequiereCadenaFrio() != null) {
            entity.setRequiereCadenaFrio(dto.getRequiereCadenaFrio());
        }
        return entity;
    }

    public MedicamentoResponseDTO toResponseDTO(Medicamento entity) {
        if (entity == null) {
            return null;
        }
        MedicamentoResponseDTO dto = new MedicamentoResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPrincipioActivo(entity.getPrincipioActivo());
        dto.setRequiereCadenaFrio(entity.isRequiereCadenaFrio());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
