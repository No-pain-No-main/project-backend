package com.adanext.NoPainNoMain.domain;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.domain.types.UserStatus;
import java.time.LocalDate;

public class Student {

  private String firstName;
  private String middleName; // Puede ser null
  private String lastName;
  private String secondLastName; // Puede ser null
  private String email;
  private DocumentType documentType;
  private String documentNumber; // PK: número de documento
  private LocalDate birthDate;
  private String phone;
  private Gender gender;
  private UserStatus userStatus;
  private String passwordHash;

  public Student() {}

  public Student(
      String documentNumber,
      String firstName,
      String middleName,
      String lastName,
      String secondLastName,
      String email,
      DocumentType documentType,
      LocalDate birthDate,
      String phone,
      Gender gender,
      UserStatus userStatus,
      String passwordHash) {
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
    this.email = email;
    this.documentType = documentType;
    this.birthDate = birthDate;
    this.phone = phone;
    this.gender = gender;
    this.userStatus = userStatus;
    this.passwordHash = passwordHash;
  }

  // documentNumber es ahora la PK
  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public DocumentType getDocumentType() {
    return documentType;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public String getPhone() {
    return phone;
  }

  public Gender getGender() {
    return gender;
  }

  public UserStatus getUserStatus() {
    return userStatus;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getSecondLastName() {
    return secondLastName;
  }
}
