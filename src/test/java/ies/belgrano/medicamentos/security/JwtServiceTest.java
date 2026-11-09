package ies.belgrano.medicamentos.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;

import com.nimbusds.jwt.JWTClaimsSet;

import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.usuario.RolUsuario;
import ies.belgrano.medicamentos.usuario.Usuario;

class JwtServiceTest {

    private static final String SECRETO = "test-secret-key-for-unit-tests-32b-ok";
    private static final String OTRO_SECRETO = "otro-secreto-distinto-de-al-menos-32-bytes";

    private static Usuario usuario(Long id, RolUsuario rol, Long organizacionId) {
        Usuario u = new Usuario();
        u.setId(id);
        u.setEmail("x@medichain.demo");
        u.setRol(rol);
        if (organizacionId != null) {
            Organizacion o = new Organizacion();
            o.setId(organizacionId);
            u.setOrganizacion(o);
        }
        return u;
    }

    @Test
    void generaYValidaUnTokenConRolYOrganizacion() throws Exception {
        JwtService jwt = new JwtService(SECRETO, 60);

        String token = jwt.generarToken(usuario(7L, RolUsuario.LABORATORIO, 3L));
        JWTClaimsSet claims = jwt.validarToken(token);

        assertEquals("7", claims.getSubject());
        assertEquals("LABORATORIO", claims.getStringClaim("rol"));
        assertEquals(3L, ((Number) claims.getClaim("organizacionId")).longValue());
    }

    @Test
    void usuarioSinOrganizacionNoLlevaElClaim() throws Exception {
        JwtService jwt = new JwtService(SECRETO, 60);

        JWTClaimsSet claims = jwt.validarToken(jwt.generarToken(usuario(1L, RolUsuario.SUPERADMIN, null)));

        assertNull(claims.getClaim("organizacionId"));
    }

    @Test
    void rechazaUnTokenVencido() {
        JwtService jwt = new JwtService(SECRETO, -1); // vence un minuto antes de emitirse

        String token = jwt.generarToken(usuario(1L, RolUsuario.FARMACIA, 2L));

        assertThrows(CredentialsExpiredException.class, () -> jwt.validarToken(token));
    }

    @Test
    void rechazaUnTokenFirmadoConOtroSecreto() {
        String token = new JwtService(OTRO_SECRETO, 60).generarToken(usuario(1L, RolUsuario.FARMACIA, 2L));

        assertThrows(BadCredentialsException.class, () -> new JwtService(SECRETO, 60).validarToken(token));
    }

    @Test
    void rechazaUnTokenAlterado() {
        JwtService jwt = new JwtService(SECRETO, 60);
        String token = jwt.generarToken(usuario(1L, RolUsuario.FARMACIA, 2L));
        // Se cambia el contenido (payload) sin recalcular la firma
        String[] partes = token.split("\\.");
        String alterado = partes[0] + "." + partes[1].substring(0, partes[1].length() - 2) + "AA." + partes[2];

        assertThrows(BadCredentialsException.class, () -> jwt.validarToken(alterado));
    }

    @Test
    void rechazaTextoQueNoEsUnToken() {
        JwtService jwt = new JwtService(SECRETO, 60);

        assertThrows(BadCredentialsException.class, () -> jwt.validarToken("no-es-un-jwt"));
    }

    @Test
    void noArrancaConSecretoCortoOVacio() {
        assertThrows(IllegalStateException.class, () -> new JwtService("corto", 60));
        assertThrows(IllegalStateException.class, () -> new JwtService("", 60));
        assertThrows(IllegalStateException.class, () -> new JwtService(null, 60));
    }

    @Test
    void expiracionEnSegundos() {
        assertEquals(3600L, new JwtService(SECRETO, 60).getExpiracionSegundos());
        assertNotNull(new JwtService(SECRETO, 5));
    }
}
