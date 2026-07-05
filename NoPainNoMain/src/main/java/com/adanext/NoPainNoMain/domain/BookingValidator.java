package com.adanext.NoPainNoMain.domain;

public class BookingValidator {

    private final String documentNumber;
    private final String firstName;
    private final String lastName;
    private String passwordHash;
    private final boolean active;

    public BookingValidator() {
        this.documentNumber = null;
        this.firstName = null;
        this.lastName = null;
        this.passwordHash = null;
        this.active = true;
    }

    public BookingValidator(String documentNumber, String firstName, String lastName,
                            String passwordHash, boolean active) {
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.active = active;
    }

    public void registerPassword(String newHash) {
        if (newHash == null || newHash.isBlank()) {
            throw new IllegalArgumentException("El hash de contraseña no puede ser nulo o vacío");
        }
        this.passwordHash = newHash;
    }

    public String getDocumentNumber() { return documentNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isActive() { return active; }
}