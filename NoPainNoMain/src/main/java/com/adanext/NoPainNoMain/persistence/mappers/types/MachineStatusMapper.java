package com.adanext.NoPainNoMain.persistence.mappers.types;

import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.persistence.types.MachineStatusEntity;

public class MachineStatusMapper {

    // Convierte de Entidad de Base de Datos a Objeto de Dominio
    public static MachineStatus toDomain(MachineStatusEntity entity) {
        if (entity == null) return null;
        
        return new MachineStatus(
            entity.getId(),
            entity.getName()
        );
    }

    // Convierte de Objeto de Dominio a Entidad de Base de Datos
    public static MachineStatusEntity toEntity(MachineStatus domain) {
        if (domain == null) return null;
        
        return new MachineStatusEntity(
            domain.getId(),
            domain.getName()
        );
    }
}
