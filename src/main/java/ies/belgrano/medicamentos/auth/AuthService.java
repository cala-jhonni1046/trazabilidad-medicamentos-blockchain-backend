package ies.belgrano.medicamentos.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.inspector.InspectorANMATMapper;
import ies.belgrano.medicamentos.inspector.InspectorANMATRepository;
import ies.belgrano.medicamentos.organizacion.OrganizacionMapper;
import ies.belgrano.medicamentos.security.JwtService;
import ies.belgrano.medicamentos.usuario.Usuario;
import ies.belgrano.medicamentos.usuario.UsuarioMapper;
import ies.belgrano.medicamentos.usuario.UsuarioRepository;

@Service
public class AuthService {

    // BCrypt de la contraseña "Demo1234!" — se usa como hash de comparación ficticia
    // cuando el usuario no existe, para evitar timing attacks
    private static final String DUMMY_HASH =
            "$2a$10$eLqH37aU7w1ILFLdvI9weu3M36I6xDZT9V3m3HUm.JWVOfSHnifHe";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UsuarioMapper usuarioMapper;
    private final OrganizacionMapper organizacionMapper;
    private final InspectorANMATRepository inspectorRepository;
    private final InspectorANMATMapper inspectorMapper;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UsuarioMapper usuarioMapper,
                       OrganizacionMapper organizacionMapper,
                       InspectorANMATRepository inspectorRepository,
                       InspectorANMATMapper inspectorMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.usuarioMapper = usuarioMapper;
        this.organizacionMapper = organizacionMapper;
        this.inspectorRepository = inspectorRepository;
        this.inspectorMapper = inspectorMapper;
    }

    private static final String ERROR_GENERICO = "Credenciales inválidas.";

    /**
     * Realiza el login. Siempre hace BCrypt compare (incluso para emails inexistentes)
     * para evitar timing attacks. Devuelve LoginResponseDTO si ok, lanza
     * CredencialesInvalidasException (que el controller traduce a 401) si falla.
     */
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO dto) {
        String emailNorm = dto.getEmail() != null ? dto.getEmail().trim().toLowerCase() : "";

        Usuario usuario = usuarioRepository.findByEmail(emailNorm).orElse(null);

        if (usuario == null) {
            // Comparar con un hash ficticio para evitar timing attack
            passwordEncoder.matches(dto.getPassword() != null ? dto.getPassword() : "", DUMMY_HASH);
            throw new CredencialesInvalidasException(ERROR_GENERICO);
        }

        // BCrypt compare even when user exists
        boolean passwordOk = passwordEncoder.matches(
                dto.getPassword() != null ? dto.getPassword() : "",
                usuario.getPasswordHash()
        );

        if (!passwordOk || !usuario.isActivo()) {
            throw new CredencialesInvalidasException(ERROR_GENERICO);
        }

        String token = jwtService.generarToken(usuario);

        InspectorANMAT inspector = inspectorRepository.findByUsuarioId(usuario.getId()).orElse(null);

        return new LoginResponseDTO(
                token,
                "Bearer",
                jwtService.getExpiracionSegundos(),
                usuarioMapper.toResponseDTO(usuario),
                usuario.getOrganizacion() != null ? organizacionMapper.toResponseDTO(usuario.getOrganizacion()) : null,
                inspector != null ? inspectorMapper.toResponseDTO(inspector) : null
        );
    }

    /**
     * Construye la respuesta de /me a partir del usuario autenticado (recargado de DB).
     */
    @Transactional(readOnly = true)
    public LoginResponseDTO me(Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new CredencialesInvalidasException(ERROR_GENERICO));

        if (!usuario.isActivo()) {
            throw new CredencialesInvalidasException(ERROR_GENERICO);
        }

        InspectorANMAT inspector = inspectorRepository.findByUsuarioId(usuario.getId()).orElse(null);

        return new LoginResponseDTO(
                null,
                "Bearer",
                jwtService.getExpiracionSegundos(),
                usuarioMapper.toResponseDTO(usuario),
                usuario.getOrganizacion() != null ? organizacionMapper.toResponseDTO(usuario.getOrganizacion()) : null,
                inspector != null ? inspectorMapper.toResponseDTO(inspector) : null
        );
    }
}

