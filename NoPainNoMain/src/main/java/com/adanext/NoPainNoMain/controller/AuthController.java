package com.adanext.NoPainNoMain.controller;

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
}