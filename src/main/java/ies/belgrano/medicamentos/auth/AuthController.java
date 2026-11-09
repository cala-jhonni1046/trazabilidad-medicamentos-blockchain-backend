package ies.belgrano.medicamentos.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ies.belgrano.medicamentos.security.AuthUser;
import ies.belgrano.medicamentos.security.LoginRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final LoginRateLimiter rateLimiter;

    public AuthController(AuthService authService, LoginRateLimiter rateLimiter) {
        this.authService = authService;
        this.rateLimiter = rateLimiter;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto, HttpServletRequest request) {
        String ip = obtenerIp(request);
        String email = dto.getEmail() != null ? dto.getEmail().trim().toLowerCase() : "";

        if (rateLimiter.isBlocked(email, ip)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(java.util.Map.of(
                            "status", 429,
                            "code", ies.belgrano.medicamentos.utils.ApiErrorCode.CREDENCIALES_INVALIDAS.name(),
                            "message", "Demasiados intentos fallidos. Intente nuevamente en 5 minutos."
                    ));
        }

        try {
            LoginResponseDTO response = authService.login(dto);
            rateLimiter.limpiarExito(email, ip);
            return ResponseEntity.ok(response);
        } catch (CredencialesInvalidasException e) {
            rateLimiter.registrarFallo(email, ip);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(java.util.Map.of(
                            "status", 401,
                            "code", ies.belgrano.medicamentos.utils.ApiErrorCode.CREDENCIALES_INVALIDAS.name(),
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> me(@AuthenticationPrincipal AuthUser authUser) {
        LoginResponseDTO response = authService.me(authUser.getId());
        return ResponseEntity.ok(response);
    }

    private String obtenerIp(HttpServletRequest request) {
        // No se confía en X-Forwarded-For: un atacante podría cambiarlo en cada intento y saltear el
        // límite. Si se despliega detrás de un proxy propio, configurar server.forward-headers-strategy.
        return request.getRemoteAddr();
    }
}

