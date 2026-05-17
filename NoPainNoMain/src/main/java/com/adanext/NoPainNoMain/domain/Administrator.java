package com.adanext.NoPainNoMain.domain;
import com.adanext.NoPainNoMain.domain.types.DocumentType;

public class Administrator {

    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private DocumentType documentType;
    private String documentNumber;
    private String phone;
    private String position;
    private String passwordHash;
    private String secretPhrase;

    public Administrator() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSecondLastName() { return secondLastName; }
    public void setSecondLastName(String secondLastName) { this.secondLastName = secondLastName; }

    public DocumentType getDocumentType() { return documentType; }
    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getSecretPhrase() { return secretPhrase; }
    public void setSecretPhrase(String secretPhrase) { this.secretPhrase = secretPhrase; }
}