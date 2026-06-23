package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.persistence.types.DocumentTypeEntity;

public class DocumentTypeMapper {

    public static DocumentType toDomain(DocumentTypeEntity entity) {
        if (entity == null) return null;
        
        return new DocumentType(
            entity.getId(),
            entity.getName()
        );
    }

    public static DocumentTypeEntity toEntity(DocumentType domain) {
        if (domain == null) return null;
        
        return new DocumentTypeEntity(
            domain.getId(),
            domain.getName()
        );
    }
}