package com.adanext.NoPainNoMain.mapper;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.mapper.types.DocumentTypeMapper;
import com.adanext.NoPainNoMain.mapper.types.GenderMapper;
import com.adanext.NoPainNoMain.mapper.types.UserStatusMapper;
import com.adanext.NoPainNoMain.persistence.entities.StudentEntity;


public class StudentMapper {

    public static Student toDomain(StudentEntity entity) {
        if (entity == null) return null;

        return new Student(
            entity.getDocumentNumber(),
            entity.getFirstName(),
            entity.getMiddleName(),
            entity.getLastName(),
            entity.getSecondLastName(),
            entity.getEmail(),
            DocumentTypeMapper.toDomain(entity.getDocumentType()),
            entity.getBirthDate(),
            entity.getPhone(),
            GenderMapper.toDomain(entity.getGender()),
            UserStatusMapper.toDomain(entity.getUserStatus()),
            entity.getPasswordHash()
        );
    }

    public static StudentEntity toEntity(Student domain) {
        if (domain == null) return null;

        StudentEntity entity = new StudentEntity();
        entity.setDocumentNumber(domain.getDocumentNumber());
        entity.setFirstName(domain.getFirstName());
        entity.setMiddleName(domain.getMiddleName());
        entity.setLastName(domain.getLastName());
        entity.setSecondLastName(domain.getSecondLastName());
        entity.setEmail(domain.getEmail());
        entity.setDocumentType(DocumentTypeMapper.toEntity(domain.getDocumentType()));
        entity.setBirthDate(domain.getBirthDate());
        entity.setPhone(domain.getPhone());
        entity.setGender(GenderMapper.toEntity(domain.getGender()));
        entity.setUserStatus(UserStatusMapper.toEntity(domain.getUserStatus()));
        entity.setPasswordHash(domain.getPasswordHash());
        
        return entity;
    }
}