package com.adanext.NoPainNoMain.persistence.mappers.types;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.persistence.types.GenderEntity;

public class GenderMapper {

    // Convierte de Entidad de Base de Datos a Objeto de Dominio
    public static Gender toDomain(GenderEntity entity) {
        if (entity == null) return null;
        
        return new Gender(
            entity.getId(),
            entity.getName()
        );
    }

    // Convierte de Objeto de Dominio a Entidad de Base de Datos
    public static GenderEntity toEntity(Gender domain) {
        if (domain == null) return null;
        
        return new GenderEntity(
            domain.getId(),
            domain.getName()
        );
    }
}