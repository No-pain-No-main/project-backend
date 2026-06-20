package com.adanext.NoPainNoMain.service.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.StudentRepositoryImpl;

@Service
public class StudentQuery {
    private final StudentRepositoryImpl studentRepository;

    public StudentQuery(StudentRepositoryImpl studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student studentByDocumentNumber(String documentNumber){
        return studentRepository.findByDocumentNumber(documentNumber).orElse(null);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }
    
}
