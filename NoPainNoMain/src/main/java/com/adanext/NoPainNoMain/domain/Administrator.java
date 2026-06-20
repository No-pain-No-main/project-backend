package com.adanext.NoPainNoMain.domain;

import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.valueobjects.Email;
import com.adanext.NoPainNoMain.domain.valueobjects.FullName;

public class Administrator {

    private final FullName fullName;
    private final DocumentType documentType;
    private final String documentNumber; // PK: número de documento
    private final Email email;
    private final String phone;
    private final String position;
    private String passwordHash;
    private final String secretPhrase;

    public Administrator() {
        this.fullName = null;
        this.documentType = null;
        this.documentNumber = null;
        this.email = null;
        this.phone = null;
        this.position = null;
        this.passwordHash = null;
        this.secretPhrase = null;
    }

    public Administrator(String documentNumber, String firstName, String middleName, String lastName, String secondLastName,
                         DocumentType documentType, String email, String phone, String position,
                         String passwordHash, String secretPhrase) {
        this.documentNumber = documentNumber;
        this.fullName = new FullName(firstName, middleName, lastName, secondLastName);
        this.documentType = documentType;
        this.email = new Email(email);
        this.phone = phone;
        this.position = position;
        this.passwordHash = passwordHash;
        this.secretPhrase = secretPhrase;
    }

    public Administrator(String documentNumber, FullName fullName, DocumentType documentType, Email email,
                         String phone, String position, String passwordHash, String secretPhrase) {
        this.documentNumber = documentNumber;
        this.fullName = fullName;
        this.documentType = documentType;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.passwordHash = passwordHash;
        this.secretPhrase = secretPhrase;
    }

    /**
     * Registra (o cambia) la contraseña del administrador.
     * Recibe el hash ya computado desde la capa de infraestructura.
     */
    public void registerPassword(String newHash) {
        if (newHash == null || newHash.isBlank()) {
            throw new IllegalArgumentException("El hash de contraseña no puede ser nulo o vacío");
        }
        this.passwordHash = newHash;
    }

    // Campos obligatorios
    public String getId() { return documentNumber; }
    public String getFirstName() { return fullName.getFirstName(); }
    public String getLastName() { return fullName.getLastName(); }
    public DocumentType getDocumentType() { return documentType; }
    public String getDocumentNumber() { return documentNumber; }
    public String getEmail() { return email.value(); }
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public String getPasswordHash() { return passwordHash; }
    public String getSecretPhrase() { return secretPhrase; }

    // Campos opcionales
    public Optional<String> getMiddleName() {
        return fullName.getMiddleName();
    }

    public Optional<String> getSecondLastName() {
        return fullName.getSecondLastName();
    }

    // Acceso a Value Objects
    public FullName getFullName() { return fullName; }
    public Email getEmailObject() { return email; }
}