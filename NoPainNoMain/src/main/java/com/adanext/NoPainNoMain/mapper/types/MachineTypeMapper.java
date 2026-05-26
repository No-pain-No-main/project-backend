package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.MachineType;
import com.adanext.NoPainNoMain.persistence.types.MachineTypeEntity;

public class MachineTypeMapper {

    // Convierte de Entidad de Base de Datos a Objeto de Dominio
    public static MachineType toDomain(MachineTypeEntity entity) {
        if (entity == null) return null;
        
        return new MachineType(
            entity.getId(),
            entity.getName()
        );
    }

    // Convierte de Objeto de Dominio a Entidad de Base de Datos
    public static MachineTypeEntity toEntity(MachineType domain) {
        if (domain == null) return null;
        
        return new MachineTypeEntity(
            domain.getId(),
            domain.getName()
        );
    }
}