package com.adanext.NoPainNoMain.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.service.query.AdministratorQuery;
import com.adanext.NoPainNoMain.service.register.AdministratorRegister;

@RestController
@RequestMapping("/api/administrators")
public class AdministratorController {

    private final AdministratorQuery administratorQuery;
    private final AdministratorRegister administratorRegister;

    public AdministratorController(AdministratorQuery administratorQuery,
                                    AdministratorRegister administratorRegister) {
        this.administratorQuery = administratorQuery;
        this.administratorRegister = administratorRegister;
    }

    @GetMapping
    public List<Administrator> getAll() {
        return administratorQuery.findAll();
    }

    @GetMapping("/{documentNumber}")
    public Object getByDocumentNumber(@PathVariable String documentNumber) {
        Administrator admin = administratorQuery.byDocumentNumber(documentNumber);
        if (admin == null) {
            return "Administrador con documento " + documentNumber + " no encontrado";
        }
        return admin;
    }

    @PostMapping
    public Object register(@RequestBody String json) {
        try {
            Administrator saved = administratorRegister.save(json);
            return saved;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/{documentNumber}")
    public Object update(@PathVariable String documentNumber, @RequestBody Map<String, String> body) {
        Administrator existing = administratorQuery.byDocumentNumber(documentNumber);
        if (existing == null) {
            return "Administrador con documento " + documentNumber + " no encontrado";
        }

        String firstName = body.getOrDefault("firstName", existing.getFirstName());
        String lastName = body.getOrDefault("lastName", existing.getLastName());
        String email = body.getOrDefault("email", existing.getEmail());

        Administrator updated = new Administrator(
            existing.getDocumentNumber(),
            firstName,
            existing.getMiddleName(),
            lastName,
            existing.getSecondLastName(),
            existing.getDocumentType(),
            email,
            existing.getPhone(),
            existing.getPosition(),
            existing.getPasswordHash(),
            existing.getSecretPhrase()
        );

        return administratorRegister.save(updated);
    }
}