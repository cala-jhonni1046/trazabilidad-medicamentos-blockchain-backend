package ies.belgrano.medicamentos.despachologistico;

import org.springframework.stereotype.Component;

@Component
public class EventoCustodiaMapper {

    public EventoCustodiaResponseDTO toResponseDTO(EventoCustodia entity) {
        if (entity == null) {
            return null;
        }

        EventoCustodiaResponseDTO dto = new EventoCustodiaResponseDTO();
        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        dto.setFechaHoraEvento(entity.getFechaHoraEvento());

        if (entity.getDespacho() != null) {
            dto.setDespachoLogisticoId(entity.getDespacho().getId());
        }

        if (entity.getOrganizacionActor() != null) {
            dto.setOrganizacionActorId(entity.getOrganizacionActor().getId());
            dto.setOrganizacionActorNombre(entity.getOrganizacionActor().getNombre());
            dto.setOrganizacionActorCuit(entity.getOrganizacionActor().getCuit());
        }

        dto.setDescripcion(entity.getDescripcion());
        dto.setHashAnterior(entity.getHashAnterior());
        dto.setHashEvento(entity.getHashEvento());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());

        return dto;
    }
}
