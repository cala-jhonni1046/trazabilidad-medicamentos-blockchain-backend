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
import ies.belgrano.medicamentos.security.AccesoOrganizacion;

@Service
public class UnidadTrazableService {

    private final UnidadTrazableRepository repo;
    private final LoteRepository loteRepo;
    private final AccesoOrganizacion acceso;

    public UnidadTrazableService(UnidadTrazableRepository repo, LoteRepository loteRepo,
                                 AccesoOrganizacion acceso) {
        this.repo = repo;
        this.loteRepo = loteRepo;
        this.acceso = acceso;
    }

    @Transactional(readOnly = true)
    public List<UnidadTrazable> getAll(Long loteId, String codigoQr, EstadoUnidadTrazable estado) {
        if (loteId != null && !loteRepo.existsById(loteId)) {
            throw new ResourceNotFoundException("Lote no encontrado con ID: " + loteId);
        }
        String qr = (codigoQr != null && !codigoQr.isBlank()) ? codigoQr.trim() : null;
        if (!acceso.esAdminOGlobal()) {
            Long orgId = acceso.organizacionActualId();
            if (orgId == null) {
                return List.of();
            }
            return repo.findVisiblesPorOrganizacion(orgId, loteId, qr, estado);
        }
        if (loteId == null && qr == null && estado == null) {
            return repo.findAll();
        }
        return repo.findByFiltros(loteId, qr, estado);
    }

    @Transactional(readOnly = true)
    public List<UnidadTrazable> getAll(Long loteId, String codigoQr) {
        return getAll(loteId, codigoQr, null);
    }

    @Transactional(readOnly = true)
    public List<UnidadTrazable> getAll() {
        return getAll(null, null, null);
    }

    /** Lectura: la unidad es visible si su lote lo es para la organización (404 si no). */
    @Transactional(readOnly = true)
    public UnidadTrazable getById(Long id) {
        UnidadTrazable unidad = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidad trazable no encontrada con ID: " + id));
        verificarVisible(unidad, "Unidad trazable no encontrada con ID: " + id);
        return unidad;
    }

    private void verificarVisible(UnidadTrazable unidad, String mensaje404) {
        if (acceso.esAdminOGlobal()) {
            return;
        }
        Long orgId = acceso.organizacionActualId();
        if (orgId == null || unidad.getLote() == null
                || !loteRepo.esVisiblePorOrganizacion(unidad.getLote().getId(), orgId)) {
            throw new ResourceNotFoundException(mensaje404);
        }
    }

    /** Escritura: solo el laboratorio dueño del lote (o SUPERADMIN). */
    private UnidadTrazable getPropia(Long id) {
        UnidadTrazable unidad = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidad trazable no encontrada con ID: " + id));
        acceso.verificarAcceso(unidad.getLote().getLaboratorio().getId());
        return unidad;
    }

    @Transactional(readOnly = true)
    public UnidadTrazable getByCodigoQR(String codigoQr) {
        if (codigoQr == null || codigoQr.isBlank()) {
            throw new IllegalArgumentException("El código QR no puede estar vacío.");
        }
        String mensaje = "Unidad trazable no encontrada con código QR: " + codigoQr.trim();
        UnidadTrazable unidad = repo.findByCodigoUnicoQR(codigoQr.trim())
                .orElseThrow(() -> new ResourceNotFoundException(mensaje));
        verificarVisible(unidad, mensaje);
        return unidad;
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
        acceso.verificarAcceso(lote.getLaboratorio().getId()); // solo en lotes propios

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
        UnidadTrazable existente = getPropia(id);

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
        UnidadTrazable existente = getPropia(id);
        if (existente.getDispensacion() != null || existente.getEstado() == EstadoUnidadTrazable.DISPENSADA) {
            throw new IllegalStateException("No es posible eliminar una unidad trazable con dispensación registrada.");
        }
        throw new IllegalStateException("No está permitido eliminar unidades trazables para preservar el registro y trazabilidad del producto.");
    }
}