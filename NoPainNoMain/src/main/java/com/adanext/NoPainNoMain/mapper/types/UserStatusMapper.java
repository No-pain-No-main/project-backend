package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.UserStatus;
import com.adanext.NoPainNoMain.persistence.types.UserStatusEntity;

public class UserStatusMapper {

    // Convierte de Entidad de Base de Datos a Objeto de Dominio
    public static UserStatus toDomain(UserStatusEntity entity) {
        if (entity == null) return null;
        
        return new UserStatus(
            entity.getId(),
            entity.getName()
        );
    }

    // Convierte de Objeto de Dominio a Entidad de Base de Datos
    public static UserStatusEntity toEntity(UserStatus domain) {
        if (domain == null) return null;
        
        return new UserStatusEntity(
            domain.getId(),
            domain.getName()
        );
    }
}