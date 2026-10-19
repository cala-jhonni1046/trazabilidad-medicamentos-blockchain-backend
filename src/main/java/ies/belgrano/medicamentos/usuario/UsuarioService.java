package ies.belgrano.medicamentos.usuario;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.inspector.InspectorANMATRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final InspectorANMATRepository inspectorANMATRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          InspectorANMATRepository inspectorANMATRepository) {
        this.usuarioRepository = usuarioRepository;
        this.inspectorANMATRepository = inspectorANMATRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public Usuario create(UsuarioRequestDTO dto) {
        String emailNormalizado = dto.getEmail().trim().toLowerCase();
        if (usuarioRepository.existsByEmail(emailNormalizado)) {
            throw new DataIntegrityViolationException("Ya existe un usuario registrado con el email: " + emailNormalizado);
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(emailNormalizado);
        usuario.setRol(dto.getRol());
        usuario.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update(Long id, UsuarioRequestDTO dto) {
        Usuario existente = getById(id);

        String nuevoEmail = dto.getEmail().trim().toLowerCase();
        if (!nuevoEmail.equalsIgnoreCase(existente.getEmail())) {
            if (usuarioRepository.existsByEmailAndIdNot(nuevoEmail, id)) {
                throw new DataIntegrityViolationException("Ya existe otro usuario registrado con el email: " + nuevoEmail);
            }
            existente.setEmail(nuevoEmail);
        }

        if (dto.getRol() != null) {
            existente.setRol(dto.getRol());
        }

        if (dto.getActivo() != null) {
            existente.setActivo(dto.getActivo());
        }

        return usuarioRepository.save(existente);
    }

    @Transactional
    public Usuario activar(Long id) {
        Usuario existente = getById(id);
        existente.setActivo(true);
        return usuarioRepository.save(existente);
    }

    @Transactional
    public Usuario desactivar(Long id) {
        Usuario existente = getById(id);
        existente.setActivo(false);
        return usuarioRepository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        Usuario existente = getById(id);

        if (inspectorANMATRepository.existsByUsuarioId(id)) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el usuario con ID " + id + " porque tiene un perfil de inspector ANMAT asociado.");
        }

        try {
            usuarioRepository.delete(existente);
            usuarioRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el usuario con ID " + id + " debido a restricciones de integridad con registros dependientes.");
        }
    }
}
