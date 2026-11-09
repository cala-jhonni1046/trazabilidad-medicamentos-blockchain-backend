package ies.belgrano.medicamentos.security;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import ies.belgrano.medicamentos.usuario.Usuario;

@Service
public class JwtService {

    private final String secret;
    private final long expiracionMinutos;

    public JwtService(@Value("${app.jwt.secret:${JWT_SECRET:}}") String secret,
                      @Value("${app.jwt.expiracion-minutos:60}") long expiracionMinutos) {
        validarSecreto(secret);
        this.secret = secret;
        this.expiracionMinutos = expiracionMinutos;
    }

    public static void validarSecreto(String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("Configuración inválida: La variable de entorno JWT_SECRET (o app.jwt.secret) es obligatoria y no fue provista.");
        }
        if (secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException("Configuración inválida: La clave JWT_SECRET debe tener al menos 32 bytes (256 bits). Longitud actual: "
                    + secret.getBytes(StandardCharsets.UTF_8).length + " bytes.");
        }
    }

    public String generarToken(Usuario usuario) {
        try {
            Date ahora = new Date();
            Date expiracion = new Date(ahora.getTime() + (expiracionMinutos * 60 * 1000L));

            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                    .subject(String.valueOf(usuario.getId()))
                    .claim("rol", usuario.getRol().name())
                    .issueTime(ahora)
                    .expirationTime(expiracion);

            if (usuario.getOrganizacion() != null) {
                builder.claim("organizacionId", usuario.getOrganizacion().getId());
            }

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), builder.build());
            JWSSigner signer = new MACSigner(secret.getBytes(StandardCharsets.UTF_8));
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new IllegalStateException("Error al firmar el token JWT: " + e.getMessage(), e);
        }
    }

    public JWTClaimsSet validarToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secret.getBytes(StandardCharsets.UTF_8));

            if (!signedJWT.verify(verifier)) {
                throw new BadCredentialsException("Firma del token JWT inválida.");
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();
            if (expirationTime == null || expirationTime.before(new Date())) {
                throw new CredentialsExpiredException("El token JWT ha expirado.");
            }

            return claims;
        } catch (ParseException | JOSEException e) {
            throw new BadCredentialsException("Token JWT malformado o inválido: " + e.getMessage(), e);
        }
    }

    public long getExpiracionSegundos() {
        return expiracionMinutos * 60;
    }
}

