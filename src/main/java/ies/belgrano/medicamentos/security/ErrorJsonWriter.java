package ies.belgrano.medicamentos.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Escribe el cuerpo de error JSON (mismo formato que ErrorResponseDTO) para las respuestas 401 y 403
 * que se generan dentro de la cadena de seguridad, antes de llegar a los controladores.
 * Se arma a mano para no depender de la versión de Jackson (Spring Boot 4 usa Jackson 3).
 */
final class ErrorJsonWriter {

    private ErrorJsonWriter() {
    }

    static void escribir(HttpServletResponse response, int status, String code, String message, String path)
            throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String json = "{"
                + "\"timestamp\":\"" + Instant.now() + "\","
                + "\"status\":" + status + ","
                + "\"code\":\"" + escapar(code) + "\","
                + "\"message\":\"" + escapar(message) + "\","
                + "\"path\":\"" + escapar(path) + "\","
                + "\"fieldErrors\":null"
                + "}";
        response.getWriter().write(json);
    }

    private static String escapar(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(s.length() + 8);
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }
}
