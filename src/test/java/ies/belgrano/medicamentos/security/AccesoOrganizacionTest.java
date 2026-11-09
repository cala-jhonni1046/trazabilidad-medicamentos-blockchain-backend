package ies.belgrano.medicamentos.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.usuario.RolUsuario;

class AccesoOrganizacionTest {

    private final AccesoOrganizacion acceso = new AccesoOrganizacion();

    @AfterEach
    void limpiarContexto() {
        SecurityContextHolder.clearContext();
    }

    private static void autenticarComo(RolUsuario rol, Long organizacionId) {
        AuthUser user = new AuthUser(1L, "x@medichain.demo", rol, organizacionId);
        SecurityContextHolder.getContext().setAuthentication(new UsuarioAuth(user, rol));
    }

    /** Autenticación ya confirmada, con el AuthUser como principal (igual que arma el filtro JWT). */
    private static class UsuarioAuth extends UsernamePasswordAuthenticationToken {
        UsuarioAuth(AuthUser user, RolUsuario rol) {
            super(user, null, List.of(new SimpleGrantedAuthority("ROLE_" + rol.name())));
        }
    }

    @Test
    void sinSesionNoHayUsuarioNiOrganizacion() {
        assertNull(acceso.getAuthUser());
        assertNull(acceso.organizacionActualId());
        assertFalse(acceso.esAdminOGlobal());
    }

    @Test
    void superadminYAnmatVenTodo() {
        autenticarComo(RolUsuario.SUPERADMIN, null);
        assertTrue(acceso.esAdminOGlobal());
        assertDoesNotThrow(() -> acceso.verificarAcceso(99L));

        autenticarComo(RolUsuario.ANMAT, null);
        assertTrue(acceso.esAdminOGlobal());
        assertDoesNotThrow(() -> acceso.verificarAccesoMultiple(1L, 2L, 3L));
    }

    @Test
    void laEmpresaAccedeSoloASuPropiaOrganizacion() {
        autenticarComo(RolUsuario.LABORATORIO, 5L);

        assertFalse(acceso.esAdminOGlobal());
        assertEquals(5L, acceso.organizacionActualId());
        assertDoesNotThrow(() -> acceso.verificarAcceso(5L));
        assertThrows(ResourceNotFoundException.class, () -> acceso.verificarAcceso(6L));
    }

    @Test
    void recursoCompartidoPermiteAccesoSiParticipaLaOrganizacion() {
        autenticarComo(RolUsuario.FARMACIA, 3L);

        assertDoesNotThrow(() -> acceso.verificarAccesoMultiple(1L, 2L, 3L));
        assertThrows(ResourceNotFoundException.class, () -> acceso.verificarAccesoMultiple(1L, 2L, 4L));
    }

    @Test
    void usuarioDeEmpresaSinOrganizacionNoAccedeANada() {
        autenticarComo(RolUsuario.DISTRIBUIDOR, null);

        assertThrows(ResourceNotFoundException.class, () -> acceso.verificarAcceso(1L));
        assertThrows(ResourceNotFoundException.class, () -> acceso.verificarAccesoMultiple(1L, 2L));
        assertThrows(ResourceNotFoundException.class, () -> acceso.organizacionObligatoria());
    }

    @Test
    void organizacionObligatoriaUsaLaDelTokenYNoLaDelPedido() {
        autenticarComo(RolUsuario.LABORATORIO, 5L);
        assertEquals(5L, acceso.organizacionObligatoria());

        autenticarComo(RolUsuario.SUPERADMIN, null);
        assertNull(acceso.organizacionObligatoria()); // el admin indica la organización en el pedido
    }
}
