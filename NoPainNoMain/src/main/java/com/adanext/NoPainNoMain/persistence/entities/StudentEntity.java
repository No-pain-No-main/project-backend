package com.adanext.NoPainNoMain.persistence.entities;

import java.time.LocalDate;

import com.adanext.NoPainNoMain.persistence.PersistenceConstants;
import com.adanext.NoPainNoMain.persistence.types.DocumentTypeEntity;
import com.adanext.NoPainNoMain.persistence.types.GenderEntity;
import com.adanext.NoPainNoMain.persistence.types.UserStatusEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Student", schema = PersistenceConstants.SCHEMA)
public class StudentEntity {

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "second_last_name", length = 50)
    private String secondLastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentTypeEntity documentType; 

    @Id
    @Column(name = "document_number", nullable = false, unique = true, length = 20)
    private String documentNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(length = 20)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id", nullable = false)
    private GenderEntity gender; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id", nullable = false)
    private UserStatusEntity userStatus; 

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    // Requerido por JPA
    public StudentEntity() {}

    public StudentEntity(String documentNumber, String firstName, String middleName, String lastName, String secondLastName, 
                         String email, DocumentTypeEntity documentType, LocalDate birthDate, 
                         String phone, GenderEntity gender, UserStatusEntity userStatus, String passwordHash) {
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

    // Getters y Setters
    public String getId() { return documentNumber; }
    public void setId(String documentNumber) { this.documentNumber = documentNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSecondLastName() { return secondLastName; }
    public void setSecondLastName(String secondLastName) { this.secondLastName = secondLastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public DocumentTypeEntity getDocumentType() { return documentType; }
    public void setDocumentType(DocumentTypeEntity documentType) { this.documentType = documentType; }

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public GenderEntity getGender() { return gender; }
    public void setGender(GenderEntity gender) { this.gender = gender; }

    public UserStatusEntity getUserStatus() { return userStatus; }
    public void setUserStatus(UserStatusEntity userStatus) { this.userStatus = userStatus; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}