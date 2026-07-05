package com.adanext.NoPainNoMain.service.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;

@Service
public class StudentQuery {
    private final StudentRepository studentRepository;

    public StudentQuery(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student studentByDocumentNumber(String documentNumber){
        return studentRepository.findByDocumentNumber(documentNumber).orElse(null);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }
    
}