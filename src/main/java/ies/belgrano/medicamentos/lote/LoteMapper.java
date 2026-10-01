package ies.belgrano.medicamentos.lote;

import org.springframework.stereotype.Component;

@Component
public class LoteMapper {

    public Lote toEntity(LoteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Lote entity = new Lote();
        entity.setCodigoLote(dto.getCodigoLote());
        entity.setMedicamento(dto.getMedicamento());
        entity.setLaboratorioOrigen(dto.getLaboratorioOrigen());
        entity.setCuitLogistica(dto.getCuitLogistica());
        entity.setCuitFarmaciaOrigen(dto.getCuitFarmaciaOrigen());
        entity.setPatenteCamion(dto.getPatenteCamion());
        entity.setTempMin(dto.getTempMin());
        entity.setTempMax(dto.getTempMax());
        entity.setFechaHoraFabricacion(dto.getFechaHoraFabricacion());
        entity.setEstado(dto.getEstado());
        return entity;
    }

    public LoteResponseDTO toResponseDTO(Lote entity) {
        if (entity == null) {
            return null;
        }
        LoteResponseDTO dto = new LoteResponseDTO();
        dto.setId(entity.getId());
        dto.setCodigoLote(entity.getCodigoLote());
        dto.setMedicamento(entity.getMedicamento());
        dto.setLaboratorioOrigen(entity.getLaboratorioOrigen());
        dto.setCuitLogistica(entity.getCuitLogistica());
        dto.setCuitFarmaciaOrigen(entity.getCuitFarmaciaOrigen());
        dto.setPatenteCamion(entity.getPatenteCamion());
        dto.setTempMin(entity.getTempMin());
        dto.setTempMax(entity.getTempMax());
        dto.setFechaHoraFabricacion(entity.getFechaHoraFabricacion());
        dto.setEstado(entity.getEstado());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
