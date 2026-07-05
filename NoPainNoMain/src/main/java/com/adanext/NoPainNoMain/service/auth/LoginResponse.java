package com.adanext.NoPainNoMain.service.auth;

public class LoginResponse {

    private String token;
    private String role;
    private String documentNumber;
    private String firstName;
    private String lastName;

    public LoginResponse() {}

    public LoginResponse(String token, String role, String documentNumber, String firstName, String lastName) {
        this.token = token;
        this.role = role;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}