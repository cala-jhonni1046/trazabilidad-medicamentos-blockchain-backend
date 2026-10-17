package ies.belgrano.medicamentos.organizacion;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.enlaceCUIT.EnlaceCUITRepository;
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;

@Service
public class OrganizacionService {

    private final OrganizacionRepository organizacionRepository;
    private final EnlaceCUITRepository enlaceCUITRepository;

    public OrganizacionService(OrganizacionRepository organizacionRepository,
                               EnlaceCUITRepository enlaceCUITRepository) {
        this.organizacionRepository = organizacionRepository;
        this.enlaceCUITRepository = enlaceCUITRepository;
    }

    @Transactional(readOnly = true)
    public List<Organizacion> getAll() {
        return organizacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Organizacion getById(Long id) {
        return organizacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organización no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public Organizacion getByCuit(String cuit) {
        String cuitNormalizado = OrganizacionMapper.normalizarCuit(cuit);
        return organizacionRepository.findByCuit(cuitNormalizado)
                .orElseThrow(() -> new ResourceNotFoundException("Organización no encontrada con CUIT: " + cuitNormalizado));
    }

    @Transactional
    public Organizacion create(OrganizacionRequestDTO dto) {
        String cuitNormalizado = OrganizacionMapper.normalizarCuit(dto.getCuit());
        if (organizacionRepository.existsByCuit(cuitNormalizado)) {
            throw new DataIntegrityViolationException("Ya existe una organización registrada con el CUIT: " + cuitNormalizado);
        }

        Organizacion organizacion = new Organizacion();
        organizacion.setCuit(cuitNormalizado);
        organizacion.setNombre(dto.getNombre().trim());
        organizacion.setTipo(dto.getTipo());
        return organizacionRepository.save(organizacion);
    }

    @Transactional
    public Organizacion update(Long id, OrganizacionRequestDTO dto) {
        Organizacion existente = getById(id);

        String nuevoNombre = dto.getNombre() != null ? dto.getNombre().trim() : null;
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la organización no puede estar vacío.");
        }
        existente.setNombre(nuevoNombre);

        if (dto.getTipo() != null) {
            existente.setTipo(dto.getTipo());
        }

        if (dto.getCuit() != null) {
            String cuitNormalizado = OrganizacionMapper.normalizarCuit(dto.getCuit());
            if (!cuitNormalizado.equals(existente.getCuit())) {
                if (organizacionRepository.existsByCuitAndIdNot(cuitNormalizado, id)) {
                    throw new DataIntegrityViolationException("Ya existe otra organización con el CUIT: " + cuitNormalizado);
                }
                existente.setCuit(cuitNormalizado);
            }
        }

        return organizacionRepository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        Organizacion existente = getById(id);

        if (enlaceCUITRepository.existsByLaboratorioIdOrDistribuidorIdOrFarmaciaId(id, id, id)) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar la organización con ID " + id + " porque se encuentra vinculada a uno o más enlaces CUIT.");
        }

        try {
            organizacionRepository.delete(existente);
            organizacionRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar la organización con ID " + id + " debido a restricciones de integridad con registros dependientes.");
        }
    }
}
