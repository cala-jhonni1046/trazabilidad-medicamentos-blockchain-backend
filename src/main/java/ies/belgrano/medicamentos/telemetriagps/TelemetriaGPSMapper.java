package ies.belgrano.medicamentos.telemetriagps;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import org.springframework.stereotype.Component;

@Component
public class TelemetriaGPSMapper {

    public TelemetriaGPS toEntity(TelemetriaGPSRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        TelemetriaGPS entity = new TelemetriaGPS();
        entity.setDispositivoGpsId(dto.getDispositivoGpsId());
        entity.setLatitud(dto.getLatitud());
        entity.setLongitud(dto.getLongitud());
        entity.setAltitud(dto.getAltitud());
        entity.setVelocidad(dto.getVelocidad());
        entity.setFechaHora(dto.getFechaHora());
        if (dto.getDespachoLogisticoId() != null) {
            DespachoLogistico despacho = new DespachoLogistico();
            despacho.setId(dto.getDespachoLogisticoId());
            entity.setDespachoLogistico(despacho);
        }
        entity.setDireccionAproximada(dto.getDireccionAproximada());
        return entity;
    }

    public TelemetriaGPSResponseDTO toResponseDTO(TelemetriaGPS entity) {
        if (entity == null) {
            return null;
        }
        TelemetriaGPSResponseDTO dto = new TelemetriaGPSResponseDTO();
        dto.setId(entity.getId());
        dto.setDispositivoGpsId(entity.getDispositivoGpsId());
        dto.setLatitud(entity.getLatitud());
        dto.setLongitud(entity.getLongitud());
        dto.setAltitud(entity.getAltitud());
        dto.setVelocidad(entity.getVelocidad());
        dto.setFechaHora(entity.getFechaHora());
        if (entity.getDespachoLogistico() != null) {
            dto.setDespachoLogisticoId(entity.getDespachoLogistico().getId());
        }
        dto.setDireccionAproximada(entity.getDireccionAproximada());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
