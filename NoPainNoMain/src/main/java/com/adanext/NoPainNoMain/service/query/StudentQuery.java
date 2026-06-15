package com.adanext.NoPainNoMain.service.query;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.StudentRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class StudentQuery {
  private final StudentRepositoryImpl studentRepository;

  public StudentQuery(StudentRepositoryImpl studentRepository) {
    this.studentRepository = studentRepository;
  }

  public Student studentByDocumentNumber(String documentNumber) {
    return studentRepository.findByDocumentNumber(documentNumber).orElse(null);
  }
}
