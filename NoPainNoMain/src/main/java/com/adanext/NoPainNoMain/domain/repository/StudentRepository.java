package com.adanext.NoPainNoMain.domain.repository;

import com.adanext.NoPainNoMain.domain.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
  Student save(Student student);

  Optional<Student> findByEmail(String email);

  List<Student> findAll();

  void deleteByDocumentNumber(String documentNumber);

  Optional<Student> findByDocumentNumber(String documentNumber);
}
