package com.adanext.NoPainNoMain.domain.repository;

import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.Student;

public interface StudentRepository {
    Student save(Student student);
    Optional<Student> findById(Integer id);
    Optional<Student> findByEmail(String email);
    List<Student> findAll();
    void deleteById(Integer id);
}