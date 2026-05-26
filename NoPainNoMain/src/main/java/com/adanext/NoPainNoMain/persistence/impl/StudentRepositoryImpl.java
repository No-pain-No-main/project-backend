package com.adanext.NoPainNoMain.persistence.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;
import com.adanext.NoPainNoMain.mapper.StudentMapper;
import com.adanext.NoPainNoMain.persistence.entities.StudentEntity;
import com.adanext.NoPainNoMain.persistence.Repositories.StudentJpaRepository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final StudentJpaRepository repository;

    public StudentRepositoryImpl(StudentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student save(Student student) {
        if (student == null) return null;

        StudentEntity entity = StudentMapper.toEntity(student);
        StudentEntity saved = repository.save(entity);
        return StudentMapper.toDomain(saved);
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return repository.findById(id)
            .map(StudentMapper::toDomain);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return repository.findByEmail(email)
            .map(StudentMapper::toDomain);
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll().stream()
            .map(StudentMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}