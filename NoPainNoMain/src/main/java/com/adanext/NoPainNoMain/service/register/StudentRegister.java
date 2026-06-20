package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.StudentRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.PasswordHashHelper;

@Service
public class StudentRegister {

    private final JsonToClass<Student> jsonToClass;
    private final StudentRepositoryImpl studentRepositoryImpl;
    private final PasswordHashHelper passwordHashHelper;

    public StudentRegister(JsonToClass<Student> jsonToClass, StudentRepositoryImpl studentRepositoryImpl,
                           PasswordHashHelper passwordHashHelper) {
        this.jsonToClass = jsonToClass;
        this.studentRepositoryImpl = studentRepositoryImpl;
        this.passwordHashHelper = passwordHashHelper;
    }

    public Student save(String jsonRegister) {
        Student student = jsonToClass.convert(jsonRegister, Student.class);

        // Validaciones que requieren repositorio → se quedan en el Service
        if (student.getDocumentNumber() != null
                && studentRepositoryImpl.findByDocumentNumber(student.getDocumentNumber()).isPresent()) {
            throw new IllegalStateException("El estudiante con documento " + student.getDocumentNumber() + " ya existe en el sistema");
        }

        if (student.getEmail() != null
                && studentRepositoryImpl.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalStateException("El email " + student.getEmail() + " ya está registrado por otro estudiante");
        }

        // La capa de infraestructura hashea, el dominio solo recibe el hash
        String hashed = passwordHashHelper.hashPassword(student.getPasswordHash());
        student.registerPassword(hashed);

        return studentRepositoryImpl.save(student);
    }
}