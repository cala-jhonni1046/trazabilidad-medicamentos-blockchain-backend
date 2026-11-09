package ies.belgrano.medicamentos.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ies.belgrano.medicamentos.auditoria.AuditoriaFilter;
import ies.belgrano.medicamentos.auditoria.AuditoriaService;

/**
 * Seguridad de la API: stateless, JWT y permisos por rol.
 *
 * Las reglas se evalúan en orden y gana la primera que coincide, así que las específicas van antes
 * que las generales. Al final, cualquier otra escritura queda solo para SUPERADMIN y todo lo que no
 * sea /api/** se deniega. El PACIENTE no accede a la API privada (solo a /api/auth/me y a lo público).
 *
 * Esto controla QUÉ puede hacer cada rol. QUÉ DATOS ve cada empresa lo resuelve AccesoOrganizacion
 * en los servicios.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String SUPERADMIN = "SUPERADMIN";
    private static final String ANMAT = "ANMAT";
    private static final String LABORATORIO = "LABORATORIO";
    private static final String DISTRIBUIDOR = "DISTRIBUIDOR";
    private static final String FARMACIA = "FARMACIA";

    private static final HttpMethod[] ESCRITURAS = {
            HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RestAuthenticationEntryPoint authEntryPoint;
    private final RestAccessDeniedHandler accessDeniedHandler;
    private final AuditoriaService auditoriaService;

    @Value("${app.cors.allowed-origins:http://localhost:4200}")
    private String corsAllowedOrigins;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          RestAuthenticationEntryPoint authEntryPoint,
                          RestAccessDeniedHandler accessDeniedHandler,
                          AuditoriaService auditoriaService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.auditoriaService = auditoriaService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .logout(logout -> logout.disable())
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(authEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler))
            .authorizeHttpRequests(reg -> {
                // ── Públicos ─────────────────────────────────────────────────────────
                reg.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                reg.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                reg.requestMatchers("/api/public/**", "/api/consulta-publica/**").permitAll();
                reg.requestMatchers(HttpMethod.POST, "/api/reportes-ciudadanos").permitAll();
                reg.requestMatchers("/error").permitAll();
                reg.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**")
                        .permitAll();

                // Cualquier usuario autenticado (incluido PACIENTE) puede validar su sesión
                reg.requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated();

                // ── Administración ───────────────────────────────────────────────────
                reg.requestMatchers("/api/usuarios", "/api/usuarios/**").hasRole(SUPERADMIN);
                reg.requestMatchers("/api/auditoria", "/api/auditoria/**").hasAnyRole(SUPERADMIN, ANMAT);
                reg.requestMatchers(HttpMethod.GET, "/api/inspectores", "/api/inspectores/**")
                        .hasAnyRole(SUPERADMIN, ANMAT);
                escrituras(reg, "/api/inspectores/**", SUPERADMIN);
                // Las empresas necesitan listar organizaciones para armar enlaces y despachos
                reg.requestMatchers(HttpMethod.GET, "/api/organizaciones", "/api/organizaciones/**")
                        .hasAnyRole(SUPERADMIN, ANMAT, LABORATORIO, DISTRIBUIDOR, FARMACIA);
                escrituras(reg, "/api/organizaciones/**", SUPERADMIN);

                // ── ANMAT: aprobación de enlaces, cuarentenas, reportes y registros ──
                reg.requestMatchers(HttpMethod.POST, "/api/enlaces-cuit/*/aprobar", "/api/enlaces-cuit/*/rechazar")
                        .hasAnyRole(ANMAT, SUPERADMIN);
                escrituras(reg, "/api/cuarentenas/**", ANMAT, SUPERADMIN);
                reg.requestMatchers("/api/reportes-ciudadanos", "/api/reportes-ciudadanos/**")
                        .hasAnyRole(ANMAT, SUPERADMIN);
                escrituras(reg, "/api/registros-blockchain/**", ANMAT, SUPERADMIN);

                // ── Laboratorio: producción y enlaces ────────────────────────────────
                escrituras(reg, "/api/medicamentos/**", LABORATORIO, SUPERADMIN);
                escrituras(reg, "/api/lotes/**", LABORATORIO, SUPERADMIN);
                escrituras(reg, "/api/unidades-trazables/**", LABORATORIO, SUPERADMIN);
                reg.requestMatchers(HttpMethod.POST, "/api/enlaces-cuit").hasAnyRole(LABORATORIO, SUPERADMIN);
                escrituras(reg, "/api/enlaces-cuit/**", LABORATORIO, ANMAT, SUPERADMIN);

                // ── Logística: la farmacia también recibe y registra eventos de custodia ──
                reg.requestMatchers(HttpMethod.POST,
                        "/api/despachos-logisticos/*/entregar",
                        "/api/despachos-logisticos/*/recibir",
                        "/api/despachos-logisticos/*/eventos",
                        "/api/despachos-logisticos/*/eventos-custodia")
                        .hasAnyRole(LABORATORIO, DISTRIBUIDOR, FARMACIA, SUPERADMIN);
                escrituras(reg, "/api/despachos-logisticos/**", LABORATORIO, DISTRIBUIDOR, SUPERADMIN);
                escrituras(reg, "/api/telemetrias-gps/**", ANMAT, LABORATORIO, DISTRIBUIDOR, SUPERADMIN);
                escrituras(reg, "/api/telemetrias-temperatura/**", ANMAT, LABORATORIO, DISTRIBUIDOR, SUPERADMIN);

                // ── Farmacia ─────────────────────────────────────────────────────────
                escrituras(reg, "/api/recepciones-farmacia/**", FARMACIA, SUPERADMIN);
                escrituras(reg, "/api/dispensaciones/**", FARMACIA, SUPERADMIN);

                // ── Resto de la API ──────────────────────────────────────────────────
                // Lectura: toda empresa y los roles de control; el PACIENTE queda afuera.
                reg.requestMatchers(HttpMethod.GET, "/api/**")
                        .hasAnyRole(SUPERADMIN, ANMAT, LABORATORIO, DISTRIBUIDOR, FARMACIA);
                // Cualquier otra escritura no prevista arriba: solo SUPERADMIN.
                reg.requestMatchers("/api/**").hasRole(SUPERADMIN);
                reg.anyRequest().denyAll();
            })
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // Se instancia acá (no como @Component) para que corra solo dentro de la cadena de seguridad
            .addFilterAfter(new AuditoriaFilter(auditoriaService), JwtAuthenticationFilter.class);

        return http.build();
    }

    private static void escrituras(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry reg,
            String patron, String... roles) {
        for (HttpMethod metodo : ESCRITURAS) {
            reg.requestMatchers(metodo, patron).hasAnyRole(roles);
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> origins = Arrays.stream(corsAllowedOrigins.split(","))
                .map(String::trim)
                .filter(o -> !o.isEmpty())
                .toList();
        config.setAllowedOrigins(origins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
