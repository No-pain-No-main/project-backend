package com.adanext.NoPainNoMain.service.register.helpers;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;
import org.springframework.stereotype.Component;

@Component
public class StudentRegisterHelper {

  private final StudentRepository repository;

  public StudentRegisterHelper(StudentRepository repository) {
    this.repository = repository;
  }

  public boolean isDuplicateDocument(Student student) {
    return student.getDocumentNumber() != null
        && repository.findByDocumentNumber(student.getDocumentNumber()).isPresent();
  }

  public boolean isDuplicateEmail(Student student) {
    return student.getEmail() != null && repository.findByEmail(student.getEmail()).isPresent();
  }
}
