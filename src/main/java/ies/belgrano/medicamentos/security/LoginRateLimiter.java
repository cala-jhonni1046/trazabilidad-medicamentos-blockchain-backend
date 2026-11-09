package ies.belgrano.medicamentos.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

/**
 * Rate limiter simple en memoria para el endpoint de login.
 * Máximo 5 intentos fallidos por clave (email+IP) en 5 minutos.
 */
@Component
public class LoginRateLimiter {

    private static final int MAX_INTENTOS = 5;
    private static final long VENTANA_MS = 5 * 60 * 1000L; // 5 minutos

    private final ConcurrentHashMap<String, EntradaIntento> intentos = new ConcurrentHashMap<>();

    public boolean isBlocked(String email, String ip) {
        String clave = clave(email, ip);
        EntradaIntento entrada = intentos.get(clave);
        if (entrada == null) return false;

        if (System.currentTimeMillis() - entrada.primerIntento > VENTANA_MS) {
            intentos.remove(clave);
            return false;
        }

        return entrada.contador.get() >= MAX_INTENTOS;
    }

    public void registrarFallo(String email, String ip) {
        String clave = clave(email, ip);
        intentos.compute(clave, (k, v) -> {
            if (v == null || System.currentTimeMillis() - v.primerIntento > VENTANA_MS) {
                EntradaIntento nueva = new EntradaIntento();
                nueva.primerIntento = System.currentTimeMillis();
                nueva.contador.set(1);
                return nueva;
            }
            v.contador.incrementAndGet();
            return v;
        });
    }

    public void limpiarExito(String email, String ip) {
        intentos.remove(clave(email, ip));
    }

    private String clave(String email, String ip) {
        return (email != null ? email.toLowerCase() : "") + "|" + (ip != null ? ip : "");
    }

    private static class EntradaIntento {
        volatile long primerIntento;
        final AtomicInteger contador = new AtomicInteger(0);
    }
}

