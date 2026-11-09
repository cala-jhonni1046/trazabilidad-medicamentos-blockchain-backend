package ies.belgrano.medicamentos.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LoginRateLimiterTest {

    @Test
    void bloqueaDespuesDeCincoFallos() {
        LoginRateLimiter limiter = new LoginRateLimiter();
        for (int i = 0; i < 4; i++) {
            limiter.registrarFallo("a@x.com", "1.1.1.1");
        }
        assertFalse(limiter.isBlocked("a@x.com", "1.1.1.1"));

        limiter.registrarFallo("a@x.com", "1.1.1.1");

        assertTrue(limiter.isBlocked("a@x.com", "1.1.1.1"));
    }

    @Test
    void elBloqueoEsPorEmailEIp() {
        LoginRateLimiter limiter = new LoginRateLimiter();
        for (int i = 0; i < 5; i++) {
            limiter.registrarFallo("a@x.com", "1.1.1.1");
        }

        assertFalse(limiter.isBlocked("a@x.com", "2.2.2.2"));
        assertFalse(limiter.isBlocked("b@x.com", "1.1.1.1"));
    }

    @Test
    void noDistingueMayusculasEnElEmail() {
        LoginRateLimiter limiter = new LoginRateLimiter();
        for (int i = 0; i < 5; i++) {
            limiter.registrarFallo("A@X.com", "1.1.1.1");
        }

        assertTrue(limiter.isBlocked("a@x.COM", "1.1.1.1"));
    }

    @Test
    void unLoginExitosoLimpiaLosFallos() {
        LoginRateLimiter limiter = new LoginRateLimiter();
        for (int i = 0; i < 5; i++) {
            limiter.registrarFallo("a@x.com", "1.1.1.1");
        }

        limiter.limpiarExito("a@x.com", "1.1.1.1");

        assertFalse(limiter.isBlocked("a@x.com", "1.1.1.1"));
    }
}
