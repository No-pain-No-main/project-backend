package com.adanext.NoPainNoMain.service.auth;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.JwtParameters;
import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.BookingValidator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;
import com.adanext.NoPainNoMain.domain.repository.BookingValidatorRepository;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;
import com.adanext.NoPainNoMain.service.register.helpers.PasswordHashHelper;

@Service
public class AuthService {

    private final StudentRepository studentRepository;
    private final AdministratorRepository administratorRepository;
    private final BookingValidatorRepository bookingValidatorRepository;
    private final PasswordHashHelper passwordHashHelper;
    private final JwtUtil jwtUtil;

    public AuthService(StudentRepository studentRepository,
                       AdministratorRepository administratorRepository,
                       BookingValidatorRepository bookingValidatorRepository,
                       PasswordHashHelper passwordHashHelper) {
        this.studentRepository = studentRepository;
        this.administratorRepository = administratorRepository;
        this.bookingValidatorRepository = bookingValidatorRepository;
        this.passwordHashHelper = passwordHashHelper;
        this.jwtUtil = new JwtUtil();
    }

    public LoginResponse studentLogin(String documentNumber, String rawPassword) {
        Student student = studentRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        verifyPassword(rawPassword, student.getPasswordHash());

        String token = jwtUtil.generateToken(student.getDocumentNumber(), JwtParameters.ROLE_STUDENT, JwtParameters.STUDENT_EXPIRATION_MS);
        return new LoginResponse(token, JwtParameters.ROLE_STUDENT, student.getDocumentNumber(), student.getFirstName(), student.getLastName(), student.getEmail());
    }

    public LoginResponse adminLogin(String documentNumber, String rawPassword) {
        Administrator admin = administratorRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado"));

        verifyPassword(rawPassword, admin.getPasswordHash());

        String token = jwtUtil.generateToken(admin.getId(), JwtParameters.ROLE_ADMIN, JwtParameters.ADMIN_EXPIRATION_MS);
        return new LoginResponse(token, JwtParameters.ROLE_ADMIN, admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getEmail());
    }

    public Map<String, Object> me(String documentNumber, String role) {
        Map<String, Object> userData = new LinkedHashMap<>();

        switch (role) {
            case "STUDENT" -> {
                Student student = studentRepository.findByDocumentNumber(documentNumber)
                        .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
                userData.put("id", student.getDocumentNumber());
                userData.put("documentNumber", student.getDocumentNumber());
                userData.put("firstName", student.getFirstName());
                userData.put("middleName", student.getMiddleName());
                userData.put("lastName", student.getLastName());
                userData.put("secondLastName", student.getSecondLastName());
                userData.put("email", student.getEmail());
                userData.put("role", "student");
            }
            case "ADMIN" -> {
                Administrator admin = administratorRepository.findByDocumentNumber(documentNumber)
                        .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado"));
                userData.put("id", admin.getId());
                userData.put("documentNumber", admin.getDocumentNumber());
                userData.put("firstName", admin.getFirstName());
                userData.put("lastName", admin.getLastName());
                userData.put("email", admin.getEmail());
                userData.put("role", "admin");
            }
            default -> throw new IllegalArgumentException("Rol no reconocido: " + role);
        }

        return userData;
    }

    public LoginResponse validatorLogin(String documentNumber, String rawPassword) {
        BookingValidator validator = bookingValidatorRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new IllegalArgumentException("Validador no encontrado"));

        verifyPassword(rawPassword, validator.getPasswordHash());

        String token = jwtUtil.generateToken(validator.getDocumentNumber(), JwtParameters.ROLE_VALIDATOR, JwtParameters.VALIDATOR_EXPIRATION_MS);
        return new LoginResponse(token, JwtParameters.ROLE_VALIDATOR, validator.getDocumentNumber(), validator.getFirstName(), validator.getLastName(), "");
    }

    private void verifyPassword(String rawPassword, String hashedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("Contraseña requerida");
        }
        if (!passwordHashHelper.matches(rawPassword, hashedPassword)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
    }
}