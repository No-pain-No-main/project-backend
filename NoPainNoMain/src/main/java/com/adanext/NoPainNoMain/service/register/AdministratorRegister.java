package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.persistence.impl.AdministratorRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;

@Service
public class AdministratorRegister {

    private final JsonToClass<Administrator> jsonToClass;
    private final AdministratorRepositoryImpl administratorRepository;

    public AdministratorRegister(JsonToClass<Administrator> jsonToClass, AdministratorRepositoryImpl administratorRepository) {
        this.jsonToClass = jsonToClass;
        this.administratorRepository = administratorRepository;
    }

    public Administrator save(String jsonRegister) {
        Administrator admin = jsonToClass.convert(jsonRegister, Administrator.class);

        if (isDuplicateDocument(admin)) {
            throw new IllegalStateException("El administrador con documento " + admin.getDocumentNumber() + " ya existe en el sistema");
        }

        if (isDuplicateEmail(admin)) {
            throw new IllegalStateException("El email " + admin.getEmail() + " ya está registrado por otro administrador");
        }

        return administratorRepository.save(admin);
    }

    private boolean isDuplicateDocument(Administrator admin) {
        return admin.getDocumentNumber() != null
            && administratorRepository.findByDocumentNumber(admin.getDocumentNumber()).isPresent();
    }

    private boolean isDuplicateEmail(Administrator admin) {
        return admin.getEmail() != null
            && administratorRepository.findByEmail(admin.getEmail()).isPresent();
    }
}
