package ies.belgrano.medicamentos.blockchain.registroblockchain;

import org.springframework.stereotype.Component;

@Component
public class RegistroBlockchainMapper {

    public RegistroBlockchain toEntity(RegistroBlockchainRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        RegistroBlockchain entity = new RegistroBlockchain();
        entity.setTransactionHash(dto.getTransactionHash());
        entity.setBloque(dto.getBloque());
        entity.setFirmaInspectorANMAT(dto.getFirmaInspectorANMAT());
        entity.setTimestamp(dto.getTimestamp());
        entity.setConfirmado(dto.getConfirmado());
        return entity;
    }

    public RegistroBlockchainResponseDTO toResponseDTO(RegistroBlockchain entity) {
        if (entity == null) {
            return null;
        }
        RegistroBlockchainResponseDTO dto = new RegistroBlockchainResponseDTO();
        dto.setId(entity.getId());
        dto.setTransactionHash(entity.getTransactionHash());
        dto.setBloque(entity.getBloque());
        dto.setFirmaInspectorANMAT(entity.getFirmaInspectorANMAT());
        dto.setTimestamp(entity.getTimestamp());
        dto.setConfirmado(entity.getConfirmado());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }
}
