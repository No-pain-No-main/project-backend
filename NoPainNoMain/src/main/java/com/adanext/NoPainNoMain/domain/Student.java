package com.adanext.NoPainNoMain.domain;

import java.time.LocalDate;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.domain.types.UserStatus;

public class Student {

    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private String email;
    private final DocumentType documentType;
    private final String documentNumber; // PK: número de documento
    private final LocalDate birthDate;
    private final String phone;
    private final Gender gender;
    private final UserStatus userStatus;
    private String passwordHash;

    public Student() {
        this.firstName = null;
        this.middleName = null;
        this.lastName = null;
        this.secondLastName = null;
        this.email = null;
        this.documentType = null;
        this.documentNumber = null;
        this.birthDate = null;
        this.phone = null;
        this.gender = null;
        this.userStatus = null;
        this.passwordHash = null;
    }

    public Student(String documentNumber, String firstName, String middleName, String lastName, String secondLastName,
                   String email, DocumentType documentType, LocalDate birthDate,
                   String phone, Gender gender, UserStatus userStatus, String passwordHash) {
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.email = email;
        this.documentType = documentType;
        this.birthDate = birthDate;
        this.phone = phone;
        this.gender = gender;
        this.userStatus = userStatus;
        this.passwordHash = passwordHash;
    }

    /**
     * Registra (o cambia) la contraseña del estudiante.
     * Recibe el hash ya computado desde la capa de infraestructura.
     */
    public void registerPassword(String newHash) {
        if (newHash == null || newHash.isBlank()) {
            throw new IllegalArgumentException("El hash de contraseña no puede ser nulo o vacío");
        }
        this.passwordHash = newHash;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getSecondLastName() { return secondLastName; }
    public String getEmail() { return email; }
    public DocumentType getDocumentType() { return documentType; }
    public String getDocumentNumber() { return documentNumber; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getPhone() { return phone; }
    public Gender getGender() { return gender; }
    public UserStatus getUserStatus() { return userStatus; }
    public String getPasswordHash() { return passwordHash; }

    // Setters (needed for deserialization from JSON)
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setSecondLastName(String secondLastName) { this.secondLastName = secondLastName; }
    public void setEmail(String email) { this.email = email; }
}