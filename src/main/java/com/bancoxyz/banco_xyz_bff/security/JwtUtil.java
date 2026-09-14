package com.bancoxyz.banco_xyz_bff.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utilidad para generar y validar tokens JWT.
 * Compatible con jjwt 0.12.6.
 */
@Component
public class JwtUtil {

    // Clave secreta (en producción debe estar en variables de entorno)
    private static final String SECRET_KEY =
            "banco-xyz-clave-super-secreta-para-firmar-tokens-jwt-2026";

    // Tiempo de expiración: 10 horas
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    private final SecretKey signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Genera un token JWT con el username y el rol especificado.
     */
    public String generarToken(String username, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol);
        return crearToken(claims, username);
    }

    /**
     * Crea el token JWT.
     */
    private String crearToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(signingKey)
                .compact();
    }

    /**
     * Extrae el username del token.
     */
    public String extraerUsername(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    /**
     * Extrae el rol del token.
     */
    public String extraerRol(String token) {
        return extraerClaim(token, claims -> claims.get("rol", String.class));
    }

    /**
     * Extrae la fecha de expiración del token.
     */
    public Date extraerExpiracion(String token) {
        return extraerClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un claim específico usando una función.
     */
    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerTodosLosClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token (API actualizada jjwt 0.12.x).
     */
    private Claims extraerTodosLosClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Valida si el token es correcto y no ha expirado.
     */
    public boolean validarToken(String token, String username) {
        final String usernameDelToken = extraerUsername(token);
        return usernameDelToken.equals(username) && !estaExpirado(token);
    }

    /**
     * Verifica si el token ha expirado.
     */
    private boolean estaExpirado(String token) {
        return extraerExpiracion(token).before(new Date());
    }
}
