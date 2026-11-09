package ies.belgrano.medicamentos.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import ies.belgrano.medicamentos.utils.ApiErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Responde 401 con el formato de error de la API cuando falta el token o es inválido. */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorJsonWriter.escribir(response, HttpServletResponse.SC_UNAUTHORIZED,
                ApiErrorCode.NO_AUTENTICADO.name(),
                "No autenticado o token inválido.",
                request.getRequestURI());
    }
}
