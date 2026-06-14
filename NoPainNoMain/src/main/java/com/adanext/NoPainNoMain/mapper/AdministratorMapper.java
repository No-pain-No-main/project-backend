package com.adanext.NoPainNoMain.mapper;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.mapper.types.DocumentTypeMapper;
import com.adanext.NoPainNoMain.persistence.entities.AdministratorEntity;

public class AdministratorMapper {

    public static Administrator toDomain(AdministratorEntity entity) {
        if (entity == null) return null;

        return new Administrator(
            entity.getId(), // documentNumber
            entity.getFirstName(),
            entity.getMiddleName(),
            entity.getLastName(),
            entity.getSecondLastName(),
            DocumentTypeMapper.toDomain(entity.getDocumentType()),
            entity.getEmail(),
            entity.getPhone(),
            entity.getPosition(),
            entity.getPasswordHash(),
            entity.getSecretPhrase()
        );
    }

    public static AdministratorEntity toEntity(Administrator domain) {
        if (domain == null) return null;

        AdministratorEntity entity = new AdministratorEntity();
        entity.setId(domain.getId()); // documentNumber
        entity.setFirstName(domain.getFirstName());
        entity.setMiddleName(domain.getMiddleName().orElse(null));
        entity.setLastName(domain.getLastName());
        entity.setSecondLastName(domain.getSecondLastName().orElse(null));
        entity.setDocumentType(DocumentTypeMapper.toEntity(domain.getDocumentType()));
        entity.setEmail(domain.getEmail());
        entity.setPhone(domain.getPhone());
        entity.setPosition(domain.getPosition());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setSecretPhrase(domain.getSecretPhrase());

        return entity;
    }
}