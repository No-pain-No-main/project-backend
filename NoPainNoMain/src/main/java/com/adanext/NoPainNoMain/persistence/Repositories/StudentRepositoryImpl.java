package com.adanext.NoPainNoMain.persistence.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;
import com.adanext.NoPainNoMain.persistence.StudentEntity;
import com.adanext.NoPainNoMain.persistence.mappers.StudentMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Student save(Student student) {
        if (student == null) return null;

        StudentEntity entity = StudentMapper.toEntity(student);
        if (student.getId() == null) {
            entityManager.persist(entity);
            entityManager.flush();
            return StudentMapper.toDomain(entity);
        }

        StudentEntity merged = entityManager.merge(entity);
        return StudentMapper.toDomain(merged);
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(StudentEntity.class, id))
            .map(StudentMapper::toDomain);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        TypedQuery<StudentEntity> query = entityManager.createQuery(
            "SELECT s FROM StudentEntity s WHERE s.email = :email", StudentEntity.class);
        query.setParameter("email", email);

        return query.getResultStream()
            .findFirst()
            .map(StudentMapper::toDomain);
    }

    @Override
    public List<Student> findAll() {
        List<StudentEntity> entities = entityManager
            .createQuery("SELECT s FROM StudentEntity s", StudentEntity.class)
            .getResultList();

        return entities.stream()
            .map(StudentMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        StudentEntity entity = entityManager.find(StudentEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}