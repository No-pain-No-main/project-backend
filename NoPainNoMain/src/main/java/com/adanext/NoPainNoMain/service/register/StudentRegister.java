package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.StudentRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;

@Service
public class StudentRegister {

    private final JsonToClass<Student> jsonToClass;
    private final StudentRepositoryImpl studentRepositoryImpl;

    // Eliminamos el atributo global 'student' del constructor
    public StudentRegister(JsonToClass<Student> jsonToClass, StudentRepositoryImpl studentRepositoryImpl) {
        this.jsonToClass = jsonToClass;
        this.studentRepositoryImpl = studentRepositoryImpl;
    }

    public Student save(String jsonRegister) {
        // 1. Convertimos el JSON a objeto de dominio (variable local)
        Student student = jsonToClass.convert(jsonRegister, Student.class);
        
        // 2. Guardamos en el repositorio y retornamos
        return studentRepositoryImpl.save(student);
    }
}