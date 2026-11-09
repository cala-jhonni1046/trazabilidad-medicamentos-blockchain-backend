package ies.belgrano.medicamentos.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;

/**
 * Componente central de control de acceso por organización (multi-tenant).
 * Usar en servicios para verificar pertenencia sin repetir lógica.
 */
@Component
public class AccesoOrganizacion {

    /**
     * Retorna el AuthUser del contexto de seguridad actual, o null si no hay sesión.
     */
    public AuthUser getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        Object principal = auth.getPrincipal();
        if (principal instanceof AuthUser authUser) return authUser;
        return null;
    }

    /**
     * Retorna el ID de la organización del usuario autenticado, o null si es admin/global.
     */
    public Long organizacionActualId() {
        AuthUser au = getAuthUser();
        return au != null ? au.getOrganizacionId() : null;
    }

    /**
     * True si el usuario es SUPERADMIN o ANMAT (ven todo sin filtro por organización).
     */
    public boolean esAdminOGlobal() {
        AuthUser au = getAuthUser();
        if (au == null) return false;
        return switch (au.getRol()) {
            case SUPERADMIN, ANMAT -> true;
            default -> false;
        };
    }

    /**
     * Verifica que la organización del recurso coincida con la del usuario autenticado.
     * Si es admin/global, siempre pasa.
     * Si no coincide → lanza ResourceNotFoundException (devuelve 404, no revela existencia).
     *
     * @param organizacionIdDelRecurso ID de la organización propietaria del recurso
     */
    public void verificarAcceso(Long organizacionIdDelRecurso) {
        if (esAdminOGlobal()) return;

        Long orgActual = organizacionActualId();
        if (orgActual == null || !orgActual.equals(organizacionIdDelRecurso)) {
            throw new ResourceNotFoundException("Recurso no encontrado.");
        }
    }

    /**
     * Verifica acceso cuando el recurso involucra varias organizaciones (EnlaceCUIT, DespachoLogistico, etc.).
     * El usuario debe pertenecer a al menos una de las organizaciones involucradas.
     */
    public void verificarAccesoMultiple(Long... organizacionIds) {
        if (esAdminOGlobal()) return;

        Long orgActual = organizacionActualId();
        if (orgActual == null) {
            throw new ResourceNotFoundException("Recurso no encontrado.");
        }

        for (Long orgId : organizacionIds) {
            if (orgActual.equals(orgId)) return;
        }

        throw new ResourceNotFoundException("Recurso no encontrado.");
    }

    /**
     * Para operaciones de escritura: retorna el organizacionId que debe usarse (el del token),
     * ignorando el que venga en el body.
     */
    public Long organizacionObligatoria() {
        if (esAdminOGlobal()) return null; // SUPERADMIN/ANMAT indican la organización en el pedido
        Long orgActual = organizacionActualId();
        if (orgActual == null) {
            throw new ResourceNotFoundException("Recurso no encontrado.");
        }
        return orgActual;
    }

    /**
     * Igual que organizacionObligatoria() pero sin fallar: devuelve null si no hay organización.
     * Preferir organizacionObligatoria() en las altas.
     */
    public Long organizacionParaCrear() {
        if (esAdminOGlobal()) return null; // admin puede especificar libremente
        return organizacionActualId();
    }
}

