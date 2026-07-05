package com.adanext.NoPainNoMain.service.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import com.adanext.NoPainNoMain.config.JwtParameters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(JwtParameters.SECRET);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un token JWT para un usuario con su rol y tiempo de expiración específico.
     */
    public String generateToken(String documentNumber, String role, long expirationMs) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(documentNumber)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extrae el subject (documentNumber) del token.
     */
    public String getDocumentNumber(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extrae el rol del token.
     */
    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    /**
     * Valida si el token es válido (no ha expirado y tiene firma correcta).
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Obtiene todos los claims del token.
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}