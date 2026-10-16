package ies.belgrano.medicamentos.despachologistico;

import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.enlaceCUIT.EnlaceCUIT;

@Component
public class DespachoLogisticoMapper {
    public DespachoLogistico toEntity(DespachoLogisticoRequestDTO dto) {
        if (dto == null) return null;
        DespachoLogistico entity = new DespachoLogistico();
        entity.setNumeroRemito(dto.getNumeroRemito()); entity.setCodigoSeguimiento(dto.getCodigoSeguimiento());
        entity.setOrigen(dto.getOrigen()); entity.setDestino(dto.getDestino());
        entity.setVehiculoPatente(dto.getVehiculoPatente()); entity.setFechaSalida(dto.getFechaSalida());
        entity.setFechaEstimadaEntrega(dto.getFechaEstimadaEntrega()); entity.setFechaEntregaReal(dto.getFechaEntregaReal());
        entity.setEstado(dto.getEstado()); entity.setTemperaturaMinimaPermitida(dto.getTemperaturaMinimaPermitida());
        entity.setTemperaturaMaximaPermitida(dto.getTemperaturaMaximaPermitida()); entity.setObservaciones(dto.getObservaciones());
        if (dto.getLoteId() != null) { Lote lote = new Lote(); lote.setId(dto.getLoteId()); entity.setLote(lote); }
        if (dto.getDistribuidorId() != null) {
            Organizacion distribuidor = new Organizacion(); distribuidor.setId(dto.getDistribuidorId());
            entity.setDistribuidor(distribuidor);
        }
        if (dto.getEnlaceCuitId() != null) {
            EnlaceCUIT enlace = new EnlaceCUIT(); enlace.setId(dto.getEnlaceCuitId()); entity.setEnlaceCuit(enlace);
        }
        return entity;
    }
    public DespachoLogisticoResponseDTO toResponseDTO(DespachoLogistico entity) {
        if (entity == null) return null;
        DespachoLogisticoResponseDTO dto = new DespachoLogisticoResponseDTO();
        dto.setId(entity.getId()); dto.setNumeroRemito(entity.getNumeroRemito());
        dto.setCodigoSeguimiento(entity.getCodigoSeguimiento()); dto.setOrigen(entity.getOrigen());
        dto.setDestino(entity.getDestino()); dto.setVehiculoPatente(entity.getVehiculoPatente());
        dto.setFechaSalida(entity.getFechaSalida()); dto.setFechaEstimadaEntrega(entity.getFechaEstimadaEntrega());
        dto.setFechaEntregaReal(entity.getFechaEntregaReal()); dto.setEstado(entity.getEstado());
        dto.setTemperaturaMinimaPermitida(entity.getTemperaturaMinimaPermitida());
        dto.setTemperaturaMaximaPermitida(entity.getTemperaturaMaximaPermitida());
        dto.setObservaciones(entity.getObservaciones());
        if (entity.getLote() != null) { dto.setLoteId(entity.getLote().getId()); dto.setCodigoLote(entity.getLote().getCodigoLote()); }
        if (entity.getDistribuidor() != null) {
            dto.setDistribuidorId(entity.getDistribuidor().getId()); dto.setDistribuidorNombre(entity.getDistribuidor().getNombre());
            dto.setDistribuidorCuit(entity.getDistribuidor().getCuit());
        }
        if (entity.getEnlaceCuit() != null) dto.setEnlaceCuitId(entity.getEnlaceCuit().getId());
        dto.setFechaCreacion(entity.getFechaCreacion()); dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
