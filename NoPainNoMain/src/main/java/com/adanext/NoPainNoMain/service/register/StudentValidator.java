package com.adanext.NoPainNoMain.service.register;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;

@Service
public class StudentValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final StudentRepository studentRepository;

    public StudentValidator(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void validate(Student student) {
        validateEmailFormat(student);
        validateName(student);
        validateBirthDate(student);
        validateDocumentNumberUniqueness(student);
        validateEmailUniqueness(student);
    }

    private void validateEmailFormat(Student student) {
        boolean isEmailNullOrBlank = student.getEmail() == null || student.getEmail().isBlank();
        boolean isEmailFormatInvalid = !Pattern.matches(EMAIL_REGEX, student.getEmail());
        if (isEmailNullOrBlank || isEmailFormatInvalid) {
            throw new IllegalStateException("El email '" + student.getEmail() + "' no tiene un formato válido");
        }
    }

    private void validateName(Student student) {
        boolean isFirstNameNullOrBlank = student.getFirstName() == null || student.getFirstName().isBlank();
        boolean isLastNameNullOrBlank = student.getLastName() == null || student.getLastName().isBlank();
        if (isFirstNameNullOrBlank || isLastNameNullOrBlank) {
            throw new IllegalStateException("El nombre y apellido son obligatorios");
        }
    }

    private void validateBirthDate(Student student) {
        boolean isBirthDateNull = student.getBirthDate() == null;
        boolean isBirthDateInFuture = !isBirthDateNull && student.getBirthDate().isAfter(LocalDate.now());
        boolean isBirthDateTooOld = !isBirthDateNull && student.getBirthDate().isBefore(LocalDate.of(1900, 1, 1));
        if (isBirthDateNull || isBirthDateInFuture || isBirthDateTooOld) {
            throw new IllegalStateException("La fecha de nacimiento no es válida: " + student.getBirthDate());
        }
    }

    private void validateDocumentNumberUniqueness(Student student) {
        boolean documentNumberExists = student.getDocumentNumber() != null
                && studentRepository.findByDocumentNumber(student.getDocumentNumber()).isPresent();
        if (documentNumberExists) {
            throw new IllegalStateException("El estudiante con documento " + student.getDocumentNumber() + " ya existe en el sistema");
        }
    }

    private void validateEmailUniqueness(Student student) {
        boolean emailAlreadyRegistered = student.getEmail() != null
                && studentRepository.findByEmail(student.getEmail()).isPresent();
        if (emailAlreadyRegistered) {
            throw new IllegalStateException("El email " + student.getEmail() + " ya está registrado por otro estudiante");
        }
    }
}