package ies.belgrano.medicamentos.lote;

import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.medicamento.Medicamento;
import ies.belgrano.medicamentos.organizacion.Organizacion;

@Component
public class LoteMapper {
    public Lote toEntity(LoteRequestDTO dto) {
        if (dto == null) return null;
        Lote entity = new Lote();
        entity.setCodigoLote(dto.getCodigoLote());
        if (dto.getMedicamentoId() != null) {
            Medicamento medicamento = new Medicamento();
            medicamento.setId(dto.getMedicamentoId());
            entity.setMedicamento(medicamento);
        }
        if (dto.getLaboratorioId() != null) {
            Organizacion laboratorio = new Organizacion();
            laboratorio.setId(dto.getLaboratorioId());
            entity.setLaboratorio(laboratorio);
        }
        entity.setTempMin(dto.getTempMin());
        entity.setTempMax(dto.getTempMax());
        entity.setFechaHoraFabricacion(dto.getFechaHoraFabricacion());
        entity.setEstado(EstadoLote.CREADO);
        return entity;
    }

    public LoteResponseDTO toResponseDTO(Lote entity) {
        if (entity == null) return null;
        LoteResponseDTO dto = new LoteResponseDTO();
        dto.setId(entity.getId());
        dto.setCodigoLote(entity.getCodigoLote());
        dto.setTempMin(entity.getTempMin());
        dto.setTempMax(entity.getTempMax());
        dto.setFechaHoraFabricacion(entity.getFechaHoraFabricacion());
        dto.setEstado(entity.getEstado());
        if (entity.getMedicamento() != null) {
            dto.setMedicamentoId(entity.getMedicamento().getId());
            dto.setMedicamentoNombre(entity.getMedicamento().getNombre());
        }
        if (entity.getLaboratorio() != null) {
            dto.setLaboratorioId(entity.getLaboratorio().getId());
            dto.setLaboratorioNombre(entity.getLaboratorio().getNombre());
            dto.setLaboratorioCuit(entity.getLaboratorio().getCuit());
        }
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
