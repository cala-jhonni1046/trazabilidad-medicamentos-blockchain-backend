package ies.belgrano.medicamentos.auditoria;

import java.io.IOException;
import java.util.Set;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Registra en la tabla de auditoría toda escritura (POST/PUT/PATCH/DELETE) sobre /api/**, con el
 * usuario, su organización y el resultado HTTP. Corre dentro de la cadena de seguridad, en el mismo
 * hilo de la request, para que el usuario autenticado siga disponible.
 * No audita el login (no hay usuario autenticado todavía).
 */
public class AuditoriaFilter extends OncePerRequestFilter {

    private static final Set<String> METODOS_AUDITADOS = Set.of("POST", "PUT", "PATCH", "DELETE");

    private final AuditoriaService auditoriaService;

    public AuditoriaFilter(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String ruta = request.getRequestURI();
        return !METODOS_AUDITADOS.contains(request.getMethod())
                || !ruta.startsWith("/api/")
                || ruta.startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } finally {
            auditoriaService.registrar(request.getMethod(), request.getRequestURI(),
                    recurso(request.getRequestURI()), "HTTP " + response.getStatus());
        }
    }

    /** "/api/lotes/5/unidades" → "lotes" */
    private static String recurso(String ruta) {
        String sinPrefijo = ruta.startsWith("/api/") ? ruta.substring(5) : ruta;
        int barra = sinPrefijo.indexOf('/');
        return barra < 0 ? sinPrefijo : sinPrefijo.substring(0, barra);
    }
}
