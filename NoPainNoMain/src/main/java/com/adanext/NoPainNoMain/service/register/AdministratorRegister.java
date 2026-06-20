package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.persistence.impl.AdministratorRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.PasswordHashHelper;

@Service
public class AdministratorRegister {

    private final JsonToClass<Administrator> jsonToClass;
    private final AdministratorRepositoryImpl administratorRepository;
    private final PasswordHashHelper passwordHashHelper;

    public AdministratorRegister(JsonToClass<Administrator> jsonToClass,
                                  AdministratorRepositoryImpl administratorRepository,
                                  PasswordHashHelper passwordHashHelper) {
        this.jsonToClass = jsonToClass;
        this.administratorRepository = administratorRepository;
        this.passwordHashHelper = passwordHashHelper;
    }

    public Administrator save(String jsonRegister) {
        Administrator admin = jsonToClass.convert(jsonRegister, Administrator.class);

        // Validaciones que requieren repositorio → se quedan en el Service
        if (admin.getDocumentNumber() != null
                && administratorRepository.findByDocumentNumber(admin.getDocumentNumber()).isPresent()) {
            throw new IllegalStateException("El administrador con documento " + admin.getDocumentNumber() + " ya existe en el sistema");
        }

        if (admin.getEmail() != null
                && administratorRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new IllegalStateException("El email " + admin.getEmail() + " ya está registrado por otro administrador");
        }

        // La capa de infraestructura hashea, el dominio solo recibe el hash
        String hashed = passwordHashHelper.hashPassword(admin.getPasswordHash());
        admin.registerPassword(hashed);

        return administratorRepository.save(admin);
    }
}