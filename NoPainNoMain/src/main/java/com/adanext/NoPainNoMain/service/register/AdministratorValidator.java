package com.adanext.NoPainNoMain.service.register;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;

@Service
public class AdministratorValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final AdministratorRepository administratorRepository;

    public AdministratorValidator(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public void validate(Administrator admin) {
        validateEmailFormat(admin);
        validateName(admin);
        validateDocumentNumberUniqueness(admin);
        validateEmailUniqueness(admin);
    }

    private void validateEmailFormat(Administrator admin) {
        boolean isEmailNullOrBlank = admin.getEmail() == null || admin.getEmail().isBlank();
        boolean isEmailFormatInvalid = !Pattern.matches(EMAIL_REGEX, admin.getEmail());
        if (isEmailNullOrBlank || isEmailFormatInvalid) {
            throw new IllegalStateException("El email '" + admin.getEmail() + "' no tiene un formato válido");
        }
    }

    private void validateName(Administrator admin) {
        boolean isFirstNameNullOrBlank = admin.getFirstName() == null || admin.getFirstName().isBlank();
        boolean isLastNameNullOrBlank = admin.getLastName() == null || admin.getLastName().isBlank();
        if (isFirstNameNullOrBlank || isLastNameNullOrBlank) {
            throw new IllegalStateException("El nombre y apellido son obligatorios");
        }
    }

    private void validateDocumentNumberUniqueness(Administrator admin) {
        boolean documentNumberExists = admin.getDocumentNumber() != null
                && administratorRepository.findByDocumentNumber(admin.getDocumentNumber()).isPresent();
        if (documentNumberExists) {
            throw new IllegalStateException("El administrador con documento " + admin.getDocumentNumber() + " ya existe en el sistema");
        }
    }

    private void validateEmailUniqueness(Administrator admin) {
        boolean emailAlreadyRegistered = admin.getEmail() != null
                && administratorRepository.findByEmail(admin.getEmail()).isPresent();
        if (emailAlreadyRegistered) {
            throw new IllegalStateException("El email " + admin.getEmail() + " ya está registrado por otro administrador");
        }
    }
}