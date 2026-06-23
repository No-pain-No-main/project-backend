package com.adanext.NoPainNoMain.domain;

import com.adanext.NoPainNoMain.domain.types.DocumentType;

public class Administrator {

    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private final DocumentType documentType;
    private final String documentNumber; // PK: número de documento
    private String email;
    private final String phone;
    private final String position;
    private String passwordHash;
    private final String secretPhrase;

    public Administrator() {
        this.firstName = null;
        this.middleName = null;
        this.lastName = null;
        this.secondLastName = null;
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
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.documentType = documentType;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.passwordHash = passwordHash;
        this.secretPhrase = secretPhrase;
    }

    public void registerPassword(String newHash) {
        if (newHash == null || newHash.isBlank()) {
            throw new IllegalArgumentException("El hash de contraseña no puede ser nulo o vacío");
        }
        this.passwordHash = newHash;
    }

    public String getId() { return documentNumber; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getSecondLastName() { return secondLastName; }
    public DocumentType getDocumentType() { return documentType; }
    public String getDocumentNumber() { return documentNumber; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public String getPasswordHash() { return passwordHash; }
    public String getSecretPhrase() { return secretPhrase; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setSecondLastName(String secondLastName) { this.secondLastName = secondLastName; }
    public void setEmail(String email) { this.email = email; }
}