package ies.belgrano.medicamentos.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.utils.ApiErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Responde 403 con el formato de error de la API cuando el rol no tiene permiso. */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ErrorJsonWriter.escribir(response, HttpServletResponse.SC_FORBIDDEN,
                ApiErrorCode.ACCESO_DENEGADO.name(),
                "Acceso denegado: no tiene permisos para esta operación.",
                request.getRequestURI());
    }
}
