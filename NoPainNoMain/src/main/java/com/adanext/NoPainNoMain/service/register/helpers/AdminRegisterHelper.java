package com.adanext.NoPainNoMain.service.register.helpers;

import org.springframework.stereotype.Component;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.persistence.impl.AdministratorRepositoryImpl;

@Component
public class AdminRegisterHelper {

    private final AdministratorRepositoryImpl repository;

    public AdminRegisterHelper(AdministratorRepositoryImpl repository) {
        this.repository = repository;
    }

    public boolean isDuplicateDocument(Administrator admin) {
        return admin.getDocumentNumber() != null
            && repository.findById(admin.getDocumentNumber()).isPresent();
    }

    public boolean isDuplicateEmail(Administrator admin) {
        return admin.getEmail() != null
            && repository.findByEmail(admin.getEmail()).isPresent();
    }
}