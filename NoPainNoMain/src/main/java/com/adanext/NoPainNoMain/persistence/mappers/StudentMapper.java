package com.adanext.NoPainNoMain.persistence.mappers;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.StudentEntity;
import com.adanext.NoPainNoMain.persistence.mappers.types.DocumentTypeMapper;
import com.adanext.NoPainNoMain.persistence.mappers.types.GenderMapper;
import com.adanext.NoPainNoMain.persistence.mappers.types.UserStatusMapper;


public class StudentMapper {

    public static Student toDomain(StudentEntity entity) {
        if (entity == null) return null;

        return new Student(
            entity.getId(),
            entity.getFirstName(),
            entity.getMiddleName(),
            entity.getLastName(),
            entity.getSecondLastName(),
            entity.getEmail(),
            DocumentTypeMapper.toDomain(entity.getDocumentType()),
            entity.getDocumentNumber(),
            entity.getBirthDate(),
            entity.getPhone(),
            GenderMapper.toDomain(entity.getGender()),
            UserStatusMapper.toDomain(entity.getUserStatus()),
            entity.getPasswordHash() // Mapea al campo de contraseña de tu dominio
        );
    }

    public static StudentEntity toEntity(Student domain) {
        if (domain == null) return null;

        StudentEntity entity = new StudentEntity();
        entity.setId(domain.getId());
        entity.setFirstName(domain.getFirstName());
        entity.setMiddleName(domain.getMiddleName().orElseThrow(() -> new RuntimeException("El dato no fue encontrado")));
        entity.setLastName(domain.getLastName());
        entity.setSecondLastName(domain.getSecondLastName().orElseThrow(() -> new RuntimeException("El dato no fue encontrado")));
        entity.setEmail(domain.getEmail());
        entity.setDocumentType(DocumentTypeMapper.toEntity(domain.getDocumentType()));
        entity.setDocumentNumber(domain.getDocumentNumber());
        entity.setBirthDate(domain.getBirthDate());
        entity.setPhone(domain.getPhone());
        entity.setGender(GenderMapper.toEntity(domain.getGender()));
        entity.setUserStatus(UserStatusMapper.toEntity(domain.getUserStatus()));
        entity.setPasswordHash(domain.getPasswordHash()); // O el método correspondiente en tu dominio
        
        return entity;
    }
}