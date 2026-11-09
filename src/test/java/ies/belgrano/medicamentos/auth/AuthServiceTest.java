package ies.belgrano.medicamentos.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ies.belgrano.medicamentos.inspector.InspectorANMATMapper;
import ies.belgrano.medicamentos.inspector.InspectorANMATRepository;
import ies.belgrano.medicamentos.organizacion.OrganizacionMapper;
import ies.belgrano.medicamentos.security.JwtService;
import ies.belgrano.medicamentos.usuario.RolUsuario;
import ies.belgrano.medicamentos.usuario.Usuario;
import ies.belgrano.medicamentos.usuario.UsuarioMapper;
import ies.belgrano.medicamentos.usuario.UsuarioRepository;

class AuthServiceTest {

    private static final String PASSWORD = "Demo1234!";

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private UsuarioRepository usuarioRepo;
    private InspectorANMATRepository inspectorRepo;
    private AuthService service;

    @BeforeEach
    void preparar() {
        usuarioRepo = mock(UsuarioRepository.class);
        inspectorRepo = mock(InspectorANMATRepository.class);
        when(inspectorRepo.findByUsuarioId(any())).thenReturn(Optional.empty());

        service = new AuthService(usuarioRepo, encoder,
                new JwtService("test-secret-key-for-unit-tests-32b-ok", 60),
                new UsuarioMapper(), new OrganizacionMapper(),
                inspectorRepo, mock(InspectorANMATMapper.class));
    }

    private Usuario usuario(boolean activo) {
        Usuario u = new Usuario();
        u.setId(10L);
        u.setEmail("lab@medichain.demo");
        u.setRol(RolUsuario.SUPERADMIN);
        u.setActivo(activo);
        u.setPasswordHash(encoder.encode(PASSWORD));
        return u;
    }

    private static LoginRequestDTO pedido(String email, String password) {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        return dto;
    }

    @Test
    void loginCorrectoDevuelveToken() {
        when(usuarioRepo.findByEmail("lab@medichain.demo")).thenReturn(Optional.of(usuario(true)));

        LoginResponseDTO respuesta = service.login(pedido("  LAB@medichain.demo ", PASSWORD));

        assertNotNull(respuesta.getToken());
        assertEquals("Bearer", respuesta.getTipo());
        assertEquals(3600L, respuesta.getExpiraEnSegundos());
    }

    @Test
    void contrasenaIncorrectaDaCredencialesInvalidas() {
        when(usuarioRepo.findByEmail("lab@medichain.demo")).thenReturn(Optional.of(usuario(true)));

        assertThrows(CredencialesInvalidasException.class,
                () -> service.login(pedido("lab@medichain.demo", "incorrecta")));
    }

    @Test
    void emailInexistenteDaElMismoError() {
        when(usuarioRepo.findByEmail("nadie@medichain.demo")).thenReturn(Optional.empty());

        CredencialesInvalidasException ex = assertThrows(CredencialesInvalidasException.class,
                () -> service.login(pedido("nadie@medichain.demo", PASSWORD)));
        when(usuarioRepo.findByEmail("lab@medichain.demo")).thenReturn(Optional.of(usuario(true)));
        CredencialesInvalidasException otro = assertThrows(CredencialesInvalidasException.class,
                () -> service.login(pedido("lab@medichain.demo", "mala")));

        // No se revela si el email existe: ambos errores dicen lo mismo
        assertEquals(ex.getMessage(), otro.getMessage());
    }

    @Test
    void usuarioInactivoNoPuedeIngresarAunqueLaContrasenaSeaCorrecta() {
        when(usuarioRepo.findByEmail("lab@medichain.demo")).thenReturn(Optional.of(usuario(false)));

        assertThrows(CredencialesInvalidasException.class,
                () -> service.login(pedido("lab@medichain.demo", PASSWORD)));
    }

    @Test
    void usuarioSinContrasenaCargadaNoPuedeIngresar() {
        Usuario sinHash = usuario(true);
        sinHash.setPasswordHash("");
        when(usuarioRepo.findByEmail("lab@medichain.demo")).thenReturn(Optional.of(sinHash));

        assertThrows(CredencialesInvalidasException.class,
                () -> service.login(pedido("lab@medichain.demo", PASSWORD)));
    }

    @Test
    void meRechazaUsuariosInactivosOInexistentes() {
        when(usuarioRepo.findById(10L)).thenReturn(Optional.of(usuario(false)));
        when(usuarioRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CredencialesInvalidasException.class, () -> service.me(10L));
        assertThrows(CredencialesInvalidasException.class, () -> service.me(99L));
    }
}
