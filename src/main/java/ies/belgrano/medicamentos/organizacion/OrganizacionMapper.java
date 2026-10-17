package ies.belgrano.medicamentos.organizacion;

import org.springframework.stereotype.Component;

@Component
public class OrganizacionMapper {

    public Organizacion toEntity(OrganizacionRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Organizacion entity = new Organizacion();
        entity.setCuit(normalizarCuit(dto.getCuit()));
        entity.setNombre(dto.getNombre() != null ? dto.getNombre().trim() : null);
        entity.setTipo(dto.getTipo());
        return entity;
    }

    public OrganizacionResponseDTO toResponseDTO(Organizacion entity) {
        if (entity == null) {
            return null;
        }
        OrganizacionResponseDTO dto = new OrganizacionResponseDTO();
        dto.setId(entity.getId());
        dto.setCuit(entity.getCuit());
        dto.setNombre(entity.getNombre());
        dto.setTipo(entity.getTipo());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }

    /**
     * Normaliza un CUIT eliminando los guiones del formato estándar XX-XXXXXXXX-X,
     * obteniendo la cadena de 11 dígitos requerida por el contrato H0.
     * No descarta caracteres arbitrarios ni modifica dígitos numéricos.
     */
    public static String normalizarCuit(String cuit) {
        if (cuit == null) {
            return null;
        }
        String limpio = cuit.trim();
        if (limpio.matches("^\\d{2}-\\d{8}-\\d$")) {
            return limpio.replace("-", "");
        }
        return limpio;
    }
}
