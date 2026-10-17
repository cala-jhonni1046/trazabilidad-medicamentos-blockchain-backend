package ies.belgrano.medicamentos.medicamento;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.lote.LoteRepository;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final LoteRepository loteRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository, LoteRepository loteRepository) {
        this.medicamentoRepository = medicamentoRepository;
        this.loteRepository = loteRepository;
    }

    @Transactional(readOnly = true)
    public List<Medicamento> getAll() {
        return medicamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Medicamento getById(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con ID: " + id));
    }

    @Transactional
    public Medicamento create(MedicamentoRequestDTO dto) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre(dto.getNombre().trim());
        medicamento.setPrincipioActivo(dto.getPrincipioActivo().trim());
        medicamento.setRequiereCadenaFrio(dto.getRequiereCadenaFrio());
        return medicamentoRepository.save(medicamento);
    }

    @Transactional
    public Medicamento update(Long id, MedicamentoRequestDTO dto) {
        Medicamento existente = getById(id);

        existente.setNombre(dto.getNombre().trim());
        existente.setPrincipioActivo(dto.getPrincipioActivo().trim());
        existente.setRequiereCadenaFrio(dto.getRequiereCadenaFrio());

        return medicamentoRepository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        Medicamento existente = getById(id);

        if (loteRepository.existsByMedicamentoId(id)) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el medicamento con ID " + id + " porque tiene lotes asociados.");
        }

        try {
            medicamentoRepository.delete(existente);
            medicamentoRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el medicamento con ID " + id + " debido a dependencias con otros registros.");
        }
    }
}



