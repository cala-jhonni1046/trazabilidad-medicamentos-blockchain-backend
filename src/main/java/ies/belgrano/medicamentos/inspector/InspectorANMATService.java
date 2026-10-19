package ies.belgrano.medicamentos.inspector;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.usuario.Usuario;
import ies.belgrano.medicamentos.usuario.UsuarioRepository;

@Service
public class InspectorANMATService {

    private final InspectorANMATRepository inspectorRepository;
    private final UsuarioRepository usuarioRepository;

    public InspectorANMATService(InspectorANMATRepository inspectorRepository,
                                 UsuarioRepository usuarioRepository) {
        this.inspectorRepository = inspectorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<InspectorANMAT> getAll() {
        return inspectorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public InspectorANMAT getById(Long id) {
        return inspectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inspector ANMAT no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public InspectorANMAT getByUsuarioId(Long usuarioId) {
        return inspectorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Inspector ANMAT no encontrado para el usuario con ID: " + usuarioId));
    }

    @Transactional
    public InspectorANMAT create(InspectorANMATRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getUsuarioId()));

        if (inspectorRepository.existsByUsuarioId(dto.getUsuarioId())) {
            throw new DataIntegrityViolationException("Ya existe un inspector asociado al usuario con ID: " + dto.getUsuarioId());
        }

        String legajo = dto.getLegajoOficial() != null ? dto.getLegajoOficial().trim() : null;
        if (legajo != null && !legajo.isEmpty()) {
            if (inspectorRepository.existsByLegajoOficial(legajo)) {
                throw new DataIntegrityViolationException("Ya existe un inspector con el legajo oficial: " + legajo);
            }
        }

        InspectorANMAT inspector = new InspectorANMAT();
        inspector.setUsuario(usuario);
        inspector.setNombreCompleto(dto.getNombreCompleto().trim());
        inspector.setLegajoOficial(legajo);
        inspector.setDni(dto.getDni() != null ? dto.getDni().trim() : null);
        inspector.setEmailInstitucional(dto.getEmailInstitucional() != null ? dto.getEmailInstitucional().trim().toLowerCase() : null);
        inspector.setJefeAutorizador(dto.getJefeAutorizador() != null ? dto.getJefeAutorizador().trim() : null);
        inspector.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoInspector.HABILITADO);
        inspector.setFechaHoraAlta(dto.getFechaHoraAlta() != null ? dto.getFechaHoraAlta() : LocalDateTime.now(ZoneOffset.UTC));

        return inspectorRepository.save(inspector);
    }

    @Transactional
    public InspectorANMAT update(Long id, InspectorANMATRequestDTO dto) {
        InspectorANMAT existente = getById(id);

        if (dto.getUsuarioId() != null && !dto.getUsuarioId().equals(existente.getUsuario().getId())) {
            Usuario nuevoUsuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getUsuarioId()));

            if (inspectorRepository.existsByUsuarioIdAndIdNot(dto.getUsuarioId(), id)) {
                throw new DataIntegrityViolationException("Ya existe otro inspector asociado al usuario con ID: " + dto.getUsuarioId());
            }
            existente.setUsuario(nuevoUsuario);
        }

        if (dto.getLegajoOficial() != null) {
            String nuevoLegajo = dto.getLegajoOficial().trim();
            if (!nuevoLegajo.isEmpty()) {
                if (inspectorRepository.existsByLegajoOficialAndIdNot(nuevoLegajo, id)) {
                    throw new DataIntegrityViolationException("Ya existe otro inspector con el legajo oficial: " + nuevoLegajo);
                }
                existente.setLegajoOficial(nuevoLegajo);
            } else {
                existente.setLegajoOficial(null);
            }
        }

        if (dto.getNombreCompleto() != null && !dto.getNombreCompleto().isBlank()) {
            existente.setNombreCompleto(dto.getNombreCompleto().trim());
        }

        if (dto.getDni() != null) {
            existente.setDni(dto.getDni().trim());
        }

        if (dto.getEmailInstitucional() != null) {
            existente.setEmailInstitucional(dto.getEmailInstitucional().trim().toLowerCase());
        }

        if (dto.getJefeAutorizador() != null) {
            existente.setJefeAutorizador(dto.getJefeAutorizador().trim());
        }

        if (dto.getEstado() != null) {
            existente.setEstado(dto.getEstado());
        }

        if (dto.getFechaHoraAlta() != null) {
            existente.setFechaHoraAlta(dto.getFechaHoraAlta());
        }

        return inspectorRepository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        InspectorANMAT existente = getById(id);

        try {
            inspectorRepository.delete(existente);
            inspectorRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el inspector con ID " + id + " debido a restricciones de integridad con registros dependientes.");
        }
    }
}
