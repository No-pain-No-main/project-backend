package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.MachineType;
import com.adanext.NoPainNoMain.persistence.types.MachineTypeEntity;

public class MachineTypeMapper {

    public static MachineType toDomain(MachineTypeEntity entity) {
        if (entity == null) return null;
        
        return new MachineType(
            entity.getId(),
            entity.getName()
        );
    }

    public static MachineTypeEntity toEntity(MachineType domain) {
        if (domain == null) return null;
        
        return new MachineTypeEntity(
            domain.getId(),
            domain.getName()
        );
    }
}