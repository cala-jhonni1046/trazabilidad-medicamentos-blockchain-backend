package ies.belgrano.medicamentos.telemetriatemperatura;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import org.springframework.stereotype.Component;

@Component
public class TelemetriaTemperaturaMapper {

    public TelemetriaTemperatura toEntity(TelemetriaTemperaturaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        TelemetriaTemperatura entity = new TelemetriaTemperatura();
        entity.setSensorId(dto.getSensorId());
        entity.setTemperatura(dto.getTemperatura());
        entity.setFechaHora(dto.getFechaHora());
        entity.setAlertaExcursion(dto.getAlertaExcursion());
        if (dto.getDespachoLogisticoId() != null) {
            DespachoLogistico despacho = new DespachoLogistico();
            despacho.setId(dto.getDespachoLogisticoId());
            entity.setDespachoLogistico(despacho);
        }
        entity.setObservaciones(dto.getObservaciones());
        return entity;
    }

    public TelemetriaTemperaturaResponseDTO toResponseDTO(TelemetriaTemperatura entity) {
        if (entity == null) {
            return null;
        }
        TelemetriaTemperaturaResponseDTO dto = new TelemetriaTemperaturaResponseDTO();
        dto.setId(entity.getId());
        dto.setSensorId(entity.getSensorId());
        dto.setTemperatura(entity.getTemperatura());
        dto.setFechaHora(entity.getFechaHora());
        dto.setAlertaExcursion(entity.getAlertaExcursion());
        if (entity.getDespachoLogistico() != null) {
            dto.setDespachoLogisticoId(entity.getDespachoLogistico().getId());
        }
        dto.setObservaciones(entity.getObservaciones());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
