package com.adanext.NoPainNoMain.persistence.entities;
import com.adanext.NoPainNoMain.persistence.PersistenceConstants;
import com.adanext.NoPainNoMain.persistence.types.DocumentTypeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Administrator", schema = PersistenceConstants.SCHEMA)
public class AdministratorEntity {

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName; 

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "second_last_name", length = 50)
    private String secondLastName; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentTypeEntity documentType; 

    @Id
    @Column(name = "document_number", nullable = false, unique = true, length = 20)
    private String documentNumber;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, length = 100)
    private String position; 

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "secret_phrase", nullable = false, length = 255)
    private String secretPhrase;
    
    public AdministratorEntity() {}

    public AdministratorEntity(String documentNumber, String firstName, String middleName, String lastName, String secondLastName, 
                               DocumentTypeEntity documentType, String email, String phone, String position, 
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

    public DocumentTypeEntity getDocumentType() { return documentType; }
    public void setDocumentType(DocumentTypeEntity documentType) { this.documentType = documentType; }

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getSecretPhrase() { return secretPhrase; }
    public void setSecretPhrase(String secretPhrase) { this.secretPhrase = secretPhrase; }
}