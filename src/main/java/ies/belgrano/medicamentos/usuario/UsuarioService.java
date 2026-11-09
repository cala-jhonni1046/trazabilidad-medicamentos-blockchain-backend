package ies.belgrano.medicamentos.usuario;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.inspector.InspectorANMATRepository;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.organizacion.OrganizacionRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final InspectorANMATRepository inspectorANMATRepository;
    private final OrganizacionRepository organizacionRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          InspectorANMATRepository inspectorANMATRepository,
                          OrganizacionRepository organizacionRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.inspectorANMATRepository = inspectorANMATRepository;
        this.organizacionRepository = organizacionRepository;
        this.passwordEncoder = passwordEncoder;
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
        String emailNormalizado = dto.getEmail() != null ? dto.getEmail().trim().toLowerCase() : "";
        if (emailNormalizado.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (usuarioRepository.existsByEmail(emailNormalizado)) {
            throw new DataIntegrityViolationException("Ya existe un usuario registrado con el email: " + emailNormalizado);
        }

        if (dto.getRol() == null) {
            throw new IllegalArgumentException("El rol es obligatorio.");
        }

        if (dto.getPassword() == null || dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }

        Organizacion organizacion = validarYObtenerOrganizacion(dto.getRol(), dto.getOrganizacionId());

        Usuario usuario = new Usuario();
        usuario.setEmail(emailNormalizado);
        usuario.setRol(dto.getRol());
        usuario.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setOrganizacion(organizacion);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update(Long id, UsuarioRequestDTO dto) {
        Usuario existente = getById(id);

        if (dto.getEmail() != null) {
            String nuevoEmail = dto.getEmail().trim().toLowerCase();
            if (!nuevoEmail.equalsIgnoreCase(existente.getEmail())) {
                if (usuarioRepository.existsByEmailAndIdNot(nuevoEmail, id)) {
                    throw new DataIntegrityViolationException("Ya existe otro usuario registrado con el email: " + nuevoEmail);
                }
                existente.setEmail(nuevoEmail);
            }
        }

        RolUsuario rolFinal = dto.getRol() != null ? dto.getRol() : existente.getRol();
        Long orgIdFinal = dto.getOrganizacionId() != null ? dto.getOrganizacionId() :
                (existente.getOrganizacion() != null ? existente.getOrganizacion().getId() : null);

        if (dto.getRol() != null || dto.getOrganizacionId() != null) {
            Organizacion organizacion = validarYObtenerOrganizacion(rolFinal, orgIdFinal);
            existente.setRol(rolFinal);
            existente.setOrganizacion(organizacion);
        }

        if (dto.getPassword() != null) {
            if (dto.getPassword().length() < 8) {
                throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
            }
            existente.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
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

    private Organizacion validarYObtenerOrganizacion(RolUsuario rol, Long organizacionId) {
        if (rol == RolUsuario.LABORATORIO || rol == RolUsuario.DISTRIBUIDOR || rol == RolUsuario.FARMACIA) {
            if (organizacionId == null) {
                throw new IllegalArgumentException("El rol " + rol + " exige una organización asociada.");
            }
            Organizacion org = organizacionRepository.findById(organizacionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Organización no encontrada con ID: " + organizacionId));
            if (!org.getTipo().name().equals(rol.name())) {
                throw new IllegalArgumentException("El tipo de la organización (" + org.getTipo()
                        + ") debe coincidir con el rol del usuario (" + rol + ").");
            }
            return org;
        } else {
            // SUPERADMIN, ANMAT, PACIENTE
            if (organizacionId != null) {
                throw new IllegalArgumentException("El rol " + rol + " no debe tener organización asignada.");
            }
            return null;
        }
    }
}
