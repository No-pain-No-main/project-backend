package com.adanext.NoPainNoMain.domain;

import java.time.LocalDate;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.domain.types.UserStatus;
import com.adanext.NoPainNoMain.domain.valueobjects.Email;
import com.adanext.NoPainNoMain.domain.valueobjects.FullName;

public class Student {

    private final FullName fullName;
    private final Email email;
    private final DocumentType documentType;
    private final String documentNumber; // PK: número de documento
    private final LocalDate birthDate;
    private final String phone;
    private final Gender gender;
    private final UserStatus userStatus;
    private String passwordHash;

    public Student() {
        this.fullName = null;
        this.email = null;
        this.documentType = null;
        this.documentNumber = null;
        this.birthDate = null;
        this.phone = null;
        this.gender = null;
        this.userStatus = null;
        this.passwordHash = null;
    }

    // Constructor que recibe datos planos (desde JSON o Mapper)
    public Student(String documentNumber, String firstName, String middleName, String lastName, String secondLastName,
                   String email, DocumentType documentType, LocalDate birthDate,
                   String phone, Gender gender, UserStatus userStatus, String passwordHash) {
        this.documentNumber = documentNumber;
        this.fullName = new FullName(firstName, middleName, lastName, secondLastName);
        this.email = new Email(email);
        this.documentType = documentType;
        this.birthDate = birthDate;
        this.phone = phone;
        this.gender = gender;
        this.userStatus = userStatus;
        this.passwordHash = passwordHash;
    }

    // Constructor completo con Value Objects
    public Student(String documentNumber, FullName fullName, Email email, DocumentType documentType,
                   LocalDate birthDate, String phone, Gender gender, UserStatus userStatus, String passwordHash) {
        this.documentNumber = documentNumber;
        this.fullName = fullName;
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
    public String getFirstName() { return fullName.getFirstName(); }
    public String getLastName() { return fullName.getLastName(); }
    public String getEmail() { return email.value(); }
    public DocumentType getDocumentType() { return documentType; }
    public String getDocumentNumber() { return documentNumber; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getPhone() { return phone; }
    public Gender getGender() { return gender; }
    public UserStatus getUserStatus() { return userStatus; }
    public String getPasswordHash() { return passwordHash; }
    public String getMiddleName() { return fullName.getMiddleName().orElse(null); }
    public String getSecondLastName() { return fullName.getSecondLastName().orElse(null); }

    // Acceso a los Value Objects
    public FullName getFullName() { return fullName; }
    public Email getEmailObject() { return email; }
}