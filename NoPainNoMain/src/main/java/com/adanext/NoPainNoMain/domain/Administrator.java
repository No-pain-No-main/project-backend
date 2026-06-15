package com.adanext.NoPainNoMain.domain;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import java.util.Optional;

public class Administrator {

  private String firstName;
  private String middleName;
  private String lastName;
  private String secondLastName;
  private DocumentType documentType;
  private String documentNumber;
  private String email;
  private String phone;
  private String position;
  private String passwordHash;
  private String secretPhrase;

  public Administrator() {}

  public Administrator(
      String documentNumber,
      String firstName,
      String middleName,
      String lastName,
      String secondLastName,
      DocumentType documentType,
      String email,
      String phone,
      String position,
      String passwordHash,
      String secretPhrase) {
    if (documentNumber == null || documentNumber.isBlank()) {
      throw new IllegalArgumentException("documentNumber cannot be blank");
    }
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("email cannot be blank");
    }
    if (firstName == null || firstName.isBlank()) {
      throw new IllegalArgumentException("firstName cannot be blank");
    }
    if (lastName == null || lastName.isBlank()) {
      throw new IllegalArgumentException("lastName cannot be blank");
    }
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

  public String getId() {
    return documentNumber;
  }

  public void setId(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public DocumentType getDocumentType() {
    return documentType;
  }

  public void setDocumentType(DocumentType documentType) {
    this.documentType = documentType;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getSecretPhrase() {
    return secretPhrase;
  }

  public void setSecretPhrase(String secretPhrase) {
    this.secretPhrase = secretPhrase;
  }

  public Optional<String> getMiddleName() {
    return Optional.ofNullable(middleName);
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public Optional<String> getSecondLastName() {
    return Optional.ofNullable(secondLastName);
  }

  public void setSecondLastName(String secondLastName) {
    this.secondLastName = secondLastName;
  }
}
