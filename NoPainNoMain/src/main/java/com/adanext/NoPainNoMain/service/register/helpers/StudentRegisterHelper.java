package com.adanext.NoPainNoMain.service.register.helpers;

import org.springframework.stereotype.Component;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.StudentRepositoryImpl;

@Component
public class StudentRegisterHelper {

    private final StudentRepositoryImpl repository;

    public StudentRegisterHelper(StudentRepositoryImpl repository) {
        this.repository = repository;
    }

    public boolean isDuplicateDocument(Student student) {
        return student.getDocumentNumber() != null
            && repository.findByDocumentNumber(student.getDocumentNumber()).isPresent();
    }

    public boolean isDuplicateEmail(Student student) {
        return student.getEmail() != null
            && repository.findByEmail(student.getEmail()).isPresent();
    }
}
