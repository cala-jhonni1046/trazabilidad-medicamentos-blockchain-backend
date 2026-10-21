package ies.belgrano.medicamentos.unidadTrazable;

import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.lote.EstadoLote;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.lote.LoteRepository;

@Service
public class UnidadTrazableService {

    private final UnidadTrazableRepository repo;
    private final LoteRepository loteRepo;

    public UnidadTrazableService(UnidadTrazableRepository repo, LoteRepository loteRepo) {
        this.repo = repo;
        this.loteRepo = loteRepo;
    }

    @Transactional(readOnly = true)
    public List<UnidadTrazable> getAll(Long loteId, String codigoQr) {
        if (codigoQr != null && !codigoQr.isBlank()) {
            return repo.findByCodigoUnicoQR(codigoQr.trim())
                    .map(List::of)
                    .orElse(Collections.emptyList());
        }
        if (loteId != null) {
            if (!loteRepo.existsById(loteId)) {
                throw new ResourceNotFoundException("Lote no encontrado con ID: " + loteId);
            }
            return repo.findByLoteId(loteId);
        }
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public List<UnidadTrazable> getAll() {
        return getAll(null, null);
    }

    @Transactional(readOnly = true)
    public UnidadTrazable getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidad trazable no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public UnidadTrazable getByCodigoQR(String codigoQr) {
        if (codigoQr == null || codigoQr.isBlank()) {
            throw new IllegalArgumentException("El código QR no puede estar vacío.");
        }
        return repo.findByCodigoUnicoQR(codigoQr.trim())
                .orElseThrow(() -> new ResourceNotFoundException("Unidad trazable no encontrada con código QR: " + codigoQr.trim()));
    }

    @Transactional
    public UnidadTrazable create(UnidadTrazableRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos de la unidad trazable son obligatorios.");
        }
        String codigoQr = dto.getCodigoUnicoQR() != null ? dto.getCodigoUnicoQR().trim() : null;
        if (codigoQr == null || codigoQr.isBlank()) {
            throw new IllegalArgumentException("El código QR es obligatorio.");
        }
        if (repo.existsByCodigoUnicoQR(codigoQr)) {
            throw new DataIntegrityViolationException("Ya existe una unidad trazable registrada con el código QR: " + codigoQr);
        }
        if (dto.getLoteId() == null) {
            throw new IllegalArgumentException("El ID de lote es obligatorio.");
        }
        Lote lote = loteRepo.findById(dto.getLoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado con ID: " + dto.getLoteId()));

        if (lote.getEstado() == EstadoLote.CERRADO) {
            throw new IllegalStateException("No es posible dar de alta una unidad para un lote en estado terminal CERRADO.");
        }

        UnidadTrazable unidad = new UnidadTrazable();
        unidad.setCodigoUnicoQR(codigoQr);
        unidad.setLote(lote);
        unidad.setEstado(EstadoUnidadTrazable.DISPONIBLE);

        return repo.save(unidad);
    }

    @Transactional
    public UnidadTrazable update(Long id, UnidadTrazableRequestDTO dto) {
        UnidadTrazable existente = getById(id);

        if (dto.getLoteId() != null && existente.getLote() != null
                && !dto.getLoteId().equals(existente.getLote().getId())) {
            throw new IllegalStateException("No está permitido modificar el lote de una unidad trazable ya registrada.");
        }

        if (dto.getEstado() != null && dto.getEstado() != existente.getEstado()) {
            throw new IllegalStateException("No está permitido modificar el estado de una unidad trazable mediante PUT genérico.");
        }

        if (dto.getCodigoUnicoQR() != null) {
            String nuevoQr = dto.getCodigoUnicoQR().trim();
            if (nuevoQr.isBlank()) {
                throw new IllegalArgumentException("El código QR no puede estar vacío.");
            }
            if (!nuevoQr.equals(existente.getCodigoUnicoQR())) {
                if (repo.existsByCodigoUnicoQRAndIdNot(nuevoQr, id)) {
                    throw new DataIntegrityViolationException("Ya existe otra unidad trazable registrada con el código QR: " + nuevoQr);
                }
                existente.setCodigoUnicoQR(nuevoQr);
            }
        }

        return repo.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        UnidadTrazable existente = getById(id);
        if (existente.getDispensacion() != null || existente.getEstado() == EstadoUnidadTrazable.DISPENSADA) {
            throw new IllegalStateException("No es posible eliminar una unidad trazable con dispensación registrada.");
        }
        throw new IllegalStateException("No está permitido eliminar unidades trazables para preservar el registro y trazabilidad del producto.");
    }
}