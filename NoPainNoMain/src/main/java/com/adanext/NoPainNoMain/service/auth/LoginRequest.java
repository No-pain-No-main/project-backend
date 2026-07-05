package com.adanext.NoPainNoMain.service.auth;

public class LoginRequest {

    private String documentNumber;
    private String email;
    private String password;
    private String secretPhrase;

    public LoginRequest() {}

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSecretPhrase() { return secretPhrase; }
    public void setSecretPhrase(String secretPhrase) { this.secretPhrase = secretPhrase; }
}