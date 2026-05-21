package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.persistence.types.DocumentTypeEntity;

public class DocumentTypeMapper {

    // Convierte de Entidad de Base de Datos a Objeto de Dominio
    public static DocumentType toDomain(DocumentTypeEntity entity) {
        if (entity == null) return null;
        
        return new DocumentType(
            entity.getId(),
            entity.getName()
        );
    }

    // Convierte de Objeto de Dominio a Entidad de Base de Datos
    public static DocumentTypeEntity toEntity(DocumentType domain) {
        if (domain == null) return null;
        
        return new DocumentTypeEntity(
            domain.getId(),
            domain.getName()
        );
    }
}