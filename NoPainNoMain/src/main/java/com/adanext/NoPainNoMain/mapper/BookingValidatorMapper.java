package com.adanext.NoPainNoMain.mapper;

import com.adanext.NoPainNoMain.domain.BookingValidator;
import com.adanext.NoPainNoMain.persistence.entities.BookingValidatorEntity;

public class BookingValidatorMapper {

    public static BookingValidator toDomain(BookingValidatorEntity entity) {
        if (entity == null) return null;

        return new BookingValidator(
            entity.getDocumentNumber(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getPasswordHash(),
            entity.isActive()
        );
    }

    public static BookingValidatorEntity toEntity(BookingValidator domain) {
        if (domain == null) return null;

        BookingValidatorEntity entity = new BookingValidatorEntity();
        entity.setDocumentNumber(domain.getDocumentNumber());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setActive(domain.isActive());

        return entity;
    }
}