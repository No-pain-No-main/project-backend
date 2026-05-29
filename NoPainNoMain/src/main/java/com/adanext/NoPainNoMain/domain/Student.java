package com.adanext.NoPainNoMain.domain;

import java.time.LocalDate;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.domain.types.UserStatus;

public class Student {

    private  Integer id;
    private  String firstName;
    private  String middleName; // Puede ser null
    private  String lastName;
    private  String secondLastName; // Puede ser null
    private  String email;
    private  DocumentType documentType;
    private  String documentNumber;
    private  LocalDate birthDate;
    private  String phone;
    private  Gender gender;
    private  UserStatus userStatus;
    private  String passwordHash;

    public Student(){}
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
    public String getMiddleName() { return middleName;}
    public String getSecondLastName() { return secondLastName;}
}