package com.adanext.NoPainNoMain.service.query;
import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.StudentRepositoryImpl;

@Service
public class StudentQuery {
    private final StudentRepositoryImpl studentRepository;

    public StudentQuery(StudentRepositoryImpl studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student studentByID(Integer id){
        return studentRepository.findById(id).orElse(null);
    }
    
}
