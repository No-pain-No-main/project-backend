package com.adanext.NoPainNoMain.service.register;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.PasswordHashHelper;

@Service
public class AdministratorRegister {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final JsonToClass<Administrator> jsonToClass;
    private final AdministratorRepository administratorRepository;
    private final PasswordHashHelper passwordHashHelper;

    public AdministratorRegister(JsonToClass<Administrator> jsonToClass,
                                  AdministratorRepository administratorRepository,
                                  PasswordHashHelper passwordHashHelper) {
        this.jsonToClass = jsonToClass;
        this.administratorRepository = administratorRepository;
        this.passwordHashHelper = passwordHashHelper;
    }

    public Administrator save(String jsonRegister) {
        Administrator admin = jsonToClass.convert(jsonRegister, Administrator.class);

        // ─── Validaciones de formato ─────────────────────────────────
        boolean isEmailNullOrBlank = admin.getEmail() == null || admin.getEmail().isBlank();
        boolean isEmailFormatInvalid = !Pattern.matches(EMAIL_REGEX, admin.getEmail());
        if (isEmailNullOrBlank || isEmailFormatInvalid) {
            throw new IllegalStateException("El email '" + admin.getEmail() + "' no tiene un formato válido");
        }

        boolean isFirstNameNullOrBlank = admin.getFirstName() == null || admin.getFirstName().isBlank();
        boolean isLastNameNullOrBlank = admin.getLastName() == null || admin.getLastName().isBlank();
        if (isFirstNameNullOrBlank || isLastNameNullOrBlank) {
            throw new IllegalStateException("El nombre y apellido son obligatorios");
        }

        // ─── Validaciones que requieren repositorio ─────────────────
        boolean documentNumberExists = admin.getDocumentNumber() != null
                && administratorRepository.findByDocumentNumber(admin.getDocumentNumber()).isPresent();
        if (documentNumberExists) {
            throw new IllegalStateException("El administrador con documento " + admin.getDocumentNumber() + " ya existe en el sistema");
        }

        boolean emailAlreadyRegistered = admin.getEmail() != null
                && administratorRepository.findByEmail(admin.getEmail()).isPresent();
        if (emailAlreadyRegistered) {
            throw new IllegalStateException("El email " + admin.getEmail() + " ya está registrado por otro administrador");
        }

        // La capa de infraestructura hashea, el dominio solo recibe el hash
        String hashed = passwordHashHelper.hashPassword(admin.getPasswordHash());
        admin.registerPassword(hashed);

        return administratorRepository.save(admin);
    }
}