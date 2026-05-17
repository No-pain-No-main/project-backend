package com.adanext.NoPainNoMain.domain;

import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.DocumentType;

public class Administrator {

    private final Integer id;
    private final String firstName;
    private final String middleName; // Puede ser null
    private final String lastName;
    private final String secondLastName; // Puede ser null
    private final DocumentType documentType;
    private final String documentNumber;
    private final String phone;
    private final String position;
    private final String passwordHash;
    private final String secretPhrase;

    public Administrator(Integer id, String firstName, String middleName, String lastName, String secondLastName,
                         DocumentType documentType, String documentNumber, String phone, String position,
                         String passwordHash, String secretPhrase) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.position = position;
        this.passwordHash = passwordHash;
        this.secretPhrase = secretPhrase;
    }

    // Campos obligatorios
    public Integer getId() { return id; }
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