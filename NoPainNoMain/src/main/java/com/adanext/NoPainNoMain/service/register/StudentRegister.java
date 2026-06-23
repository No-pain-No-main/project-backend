package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.PasswordHashHelper;

@Service
public class StudentRegister {

    private final JsonToClass<Student> jsonToClass;
    private final StudentValidator studentValidator;
    private final StudentRepository studentRepository;
    private final PasswordHashHelper passwordHashHelper;

    public StudentRegister(JsonToClass<Student> jsonToClass, StudentValidator studentValidator,
                           StudentRepository studentRepository, PasswordHashHelper passwordHashHelper) {
        this.jsonToClass = jsonToClass;
        this.studentValidator = studentValidator;
        this.studentRepository = studentRepository;
        this.passwordHashHelper = passwordHashHelper;
    }

    public Student save(String jsonRegister) {
        Student student = jsonToClass.convert(jsonRegister, Student.class);

        studentValidator.validate(student);

        String hashed = passwordHashHelper.hashPassword(student.getPasswordHash());
        student.registerPassword(hashed);

        return studentRepository.save(student);
    }
}