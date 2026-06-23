package com.adanext.NoPainNoMain.service.register;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.PasswordHashHelper;

@Service
public class StudentRegister {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final JsonToClass<Student> jsonToClass;
    private final StudentRepository studentRepository;
    private final PasswordHashHelper passwordHashHelper;

    public StudentRegister(JsonToClass<Student> jsonToClass, StudentRepository studentRepository,
                           PasswordHashHelper passwordHashHelper) {
        this.jsonToClass = jsonToClass;
        this.studentRepository = studentRepository;
        this.passwordHashHelper = passwordHashHelper;
    }

    public Student save(String jsonRegister) {
        Student student = jsonToClass.convert(jsonRegister, Student.class);

        // ─── Validaciones de formato ─────────────────────────────────
        boolean isEmailNullOrBlank = student.getEmail() == null || student.getEmail().isBlank();
        boolean isEmailFormatInvalid = !Pattern.matches(EMAIL_REGEX, student.getEmail());
        if (isEmailNullOrBlank || isEmailFormatInvalid) {
            throw new IllegalStateException("El email '" + student.getEmail() + "' no tiene un formato válido");
        }

        boolean isFirstNameNullOrBlank = student.getFirstName() == null || student.getFirstName().isBlank();
        boolean isLastNameNullOrBlank = student.getLastName() == null || student.getLastName().isBlank();
        if (isFirstNameNullOrBlank || isLastNameNullOrBlank) {
            throw new IllegalStateException("El nombre y apellido son obligatorios");
        }

        boolean isBirthDateNull = student.getBirthDate() == null;
        boolean isBirthDateInFuture = !isBirthDateNull && student.getBirthDate().isAfter(LocalDate.now());
        boolean isBirthDateTooOld = !isBirthDateNull && student.getBirthDate().isBefore(LocalDate.of(1900, 1, 1));
        if (isBirthDateNull || isBirthDateInFuture || isBirthDateTooOld) {
            throw new IllegalStateException("La fecha de nacimiento no es válida: " + student.getBirthDate());
        }

        // ─── Validaciones que requieren repositorio ─────────────────
        boolean documentNumberExists = student.getDocumentNumber() != null
                && studentRepository.findByDocumentNumber(student.getDocumentNumber()).isPresent();
        if (documentNumberExists) {
            throw new IllegalStateException("El estudiante con documento " + student.getDocumentNumber() + " ya existe en el sistema");
        }

        boolean emailAlreadyRegistered = student.getEmail() != null
                && studentRepository.findByEmail(student.getEmail()).isPresent();
        if (emailAlreadyRegistered) {
            throw new IllegalStateException("El email " + student.getEmail() + " ya está registrado por otro estudiante");
        }

        // La capa de infraestructura hashea, el dominio solo recibe el hash
        String hashed = passwordHashHelper.hashPassword(student.getPasswordHash());
        student.registerPassword(hashed);

        return studentRepository.save(student);
    }
}