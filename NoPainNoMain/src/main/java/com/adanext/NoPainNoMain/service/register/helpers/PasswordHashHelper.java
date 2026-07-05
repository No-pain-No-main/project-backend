package com.adanext.NoPainNoMain.service.register.helpers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashHelper {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordHashHelper() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    public String hashPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}