package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.persistence.types.GenderEntity;

public class GenderMapper {

    public static Gender toDomain(GenderEntity entity) {
        if (entity == null) return null;
        
        return new Gender(
            entity.getId(),
            entity.getName()
        );
    }

    public static GenderEntity toEntity(Gender domain) {
        if (domain == null) return null;
        
        return new GenderEntity(
            domain.getId(),
            domain.getName()
        );
    }
}