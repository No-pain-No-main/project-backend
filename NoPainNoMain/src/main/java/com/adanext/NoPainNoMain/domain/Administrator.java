package com.adanext.NoPainNoMain.domain;

import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.DocumentType;

public class Administrator {

    private  String firstName;
    private  String middleName; // Puede ser null
    private  String lastName;
    private  String secondLastName; // Puede ser null
    private  DocumentType documentType;
    private  String documentNumber; // PK: número de documento
    private  String phone;
    private  String position;
    private  String passwordHash;
    private  String secretPhrase;

    public Administrator() {
        // Constructor vacío para frameworks que lo requieran
    }

    public Administrator(String documentNumber, String firstName, String middleName, String lastName, String secondLastName,
                         DocumentType documentType, String phone, String position,
                         String passwordHash, String secretPhrase) {
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.documentType = documentType;
        this.phone = phone;
        this.position = position;
        this.passwordHash = passwordHash;
        this.secretPhrase = secretPhrase;
    }

    // Campos obligatorios
    public String getId() { return documentNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public DocumentType getDocumentType() { return documentType; }
    public String getDocumentNumber() { return documentNumber; }
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public String getPasswordHash() { return passwordHash; }
    public String getSecretPhrase() { return secretPhrase; }

    // Campos opcionales
    public Optional<String> getMiddleName() {
        return Optional.ofNullable(middleName);
    }

    public Optional<String> getSecondLastName() {
        return Optional.ofNullable(secondLastName);
    }
}