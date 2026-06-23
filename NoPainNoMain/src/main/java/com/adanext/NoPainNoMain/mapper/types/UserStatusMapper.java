package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.UserStatus;
import com.adanext.NoPainNoMain.persistence.types.UserStatusEntity;

public class UserStatusMapper {

    public static UserStatus toDomain(UserStatusEntity entity) {
        if (entity == null) return null;
        
        return new UserStatus(
            entity.getId(),
            entity.getName()
        );
    }

    public static UserStatusEntity toEntity(UserStatus domain) {
        if (domain == null) return null;
        
        return new UserStatusEntity(
            domain.getId(),
            domain.getName()
        );
    }
}