package com.adanext.NoPainNoMain.domain.valueobjects;

import java.util.Objects;
import java.util.Optional;

/**
 * Value Object inmutable que encapsula el nombre completo de una persona.
 */
public final class FullName {

    private final String firstName;
    private final String middleName; // Puede ser null
    private final String lastName;
    private final String secondLastName; // Puede ser null

    public FullName(String firstName, String middleName, String lastName, String secondLastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacío");
        }
        this.firstName = firstName.trim();
        this.middleName = middleName != null ? middleName.trim() : null;
        this.lastName = lastName.trim();
        this.secondLastName = secondLastName != null ? secondLastName.trim() : null;
    }

    public String getFirstName() { return firstName; }

    public Optional<String> getMiddleName() { return Optional.ofNullable(middleName); }

    public String getLastName() { return lastName; }

    public Optional<String> getSecondLastName() { return Optional.ofNullable(secondLastName); }

    /**
     * Devuelve el nombre completo formateado: "Nombre Apellido" o "Nombre SegundoNombre Apellido SegundoApellido".
     */
    public String fullName() {
        StringBuilder sb = new StringBuilder(firstName);
        if (middleName != null) {
            sb.append(" ").append(middleName);
        }
        sb.append(" ").append(lastName);
        if (secondLastName != null) {
            sb.append(" ").append(secondLastName);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return firstName.equals(fullName.firstName) &&
               Objects.equals(middleName, fullName.middleName) &&
               lastName.equals(fullName.lastName) &&
               Objects.equals(secondLastName, fullName.secondLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, secondLastName);
    }

    @Override
    public String toString() {
        return fullName();
    }
}