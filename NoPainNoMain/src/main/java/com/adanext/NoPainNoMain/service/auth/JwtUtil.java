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

    public String getDocumentNumber(String token) {
        return getClaims(token).getSubject();
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}