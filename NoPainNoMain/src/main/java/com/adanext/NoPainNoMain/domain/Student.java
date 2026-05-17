package com.adanext.NoPainNoMain.domain;

import java.time.LocalDate;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.domain.types.UserStatus;

public class Student {

    private final Integer id;
    private final String firstName;
    private final String middleName; // Puede ser null
    private final String lastName;
    private final String secondLastName; // Puede ser null
    private final String email;
    private final DocumentType documentType;
    private final String documentNumber;
    private final LocalDate birthDate;
    private final String phone;
    private final Gender gender;
    private final UserStatus userStatus;
    private final String passwordHash;

    // El constructor sigue recibiendo todos los campos, pero aceptará 'null' en los opcionales
    public Student(Integer id, String firstName, String middleName, String lastName, String secondLastName, 
                   String email, DocumentType documentType, String documentNumber, LocalDate birthDate, 
                   String phone, Gender gender, UserStatus userStatus, String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.email = email;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
        this.phone = phone;
        this.gender = gender;
        this.userStatus = userStatus;
        this.passwordHash = passwordHash;
    }

    // Campos obligatorios devuelven el tipo directo
    public Integer getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public DocumentType getDocumentType() { return documentType; }
    public String getDocumentNumber() { return documentNumber; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getPhone() { return phone; }
    public Gender getGender() { return gender; }
    public UserStatus getUserStatus() { return userStatus; }
    public String getPasswordHash() { return passwordHash; }

    // Campos opcionales devuelven un Optional para proteger el negocio
    public Optional<String> getMiddleName() { 
        return Optional.ofNullable(middleName); 
    }

    public Optional<String> getSecondLastName() { 
        return Optional.ofNullable(secondLastName); 
    }
}