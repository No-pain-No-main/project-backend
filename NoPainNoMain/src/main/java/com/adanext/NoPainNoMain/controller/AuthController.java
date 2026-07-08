package com.adanext.NoPainNoMain.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.service.auth.AuthService;
import com.adanext.NoPainNoMain.service.auth.LoginRequest;
import com.adanext.NoPainNoMain.service.auth.LoginResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/student/login")
    public LoginResponse studentLogin(@RequestBody LoginRequest request) {
        return authService.studentLogin(request.getDocumentNumber(), request.getPassword());
    }

    @PostMapping("/admin/login")
    public LoginResponse adminLogin(@RequestBody LoginRequest request) {
        return authService.adminLogin(request.getDocumentNumber(), request.getPassword());
    }

    @PostMapping("/validator/login")
    public LoginResponse validatorLogin(@RequestBody LoginRequest request) {
        return authService.validatorLogin(request.getDocumentNumber(), request.getPassword());
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("No autenticado");
        }
        String documentNumber = authentication.getName();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse("");
        return authService.me(documentNumber, role);
    }
}