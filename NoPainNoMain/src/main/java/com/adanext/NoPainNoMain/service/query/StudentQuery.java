package com.adanext.NoPainNoMain.service.query;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentQuery {
  private final StudentRepository studentRepository;

  public StudentQuery(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public Student studentByDocumentNumber(String documentNumber) {
    return studentRepository.findByDocumentNumber(documentNumber).orElse(null);
  }
}
