package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.persistence.types.MachineStatusEntity;

public class MachineStatusMapper {

    public static MachineStatus toDomain(MachineStatusEntity entity) {
        if (entity == null) return null;
        
        return new MachineStatus(
            entity.getId(),
            entity.getName()
        );
    }

    public static MachineStatusEntity toEntity(MachineStatus domain) {
        if (domain == null) return null;
        
        return new MachineStatusEntity(
            domain.getId(),
            domain.getName()
        );
    }
}
