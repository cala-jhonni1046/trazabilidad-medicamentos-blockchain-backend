package ies.belgrano.medicamentos.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.JWTClaimsSet;

import ies.belgrano.medicamentos.usuario.Usuario;
import ies.belgrano.medicamentos.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            if (!token.isEmpty()) {
                try {
                    JWTClaimsSet claims = jwtService.validarToken(token);
                    String sub = claims.getSubject();
                    if (sub != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        Long userId = Long.valueOf(sub);
                        Usuario usuario = usuarioRepository.findById(userId).orElse(null);

                        // Valida token, recarga el usuario desde la base y rechaza si está inactivo
                        if (usuario != null && usuario.isActivo()) {
                            Long orgId = usuario.getOrganizacion() != null ? usuario.getOrganizacion().getId() : null;
                            AuthUser authUser = new AuthUser(usuario.getId(), usuario.getEmail(), usuario.getRol(), orgId);

                            List<SimpleGrantedAuthority> authorities =
                                    List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));

                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(authUser, null, authorities);
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                            SecurityContextHolder.clearContext();
                        }
                    }
                } catch (Exception e) {
                    SecurityContextHolder.clearContext();
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

