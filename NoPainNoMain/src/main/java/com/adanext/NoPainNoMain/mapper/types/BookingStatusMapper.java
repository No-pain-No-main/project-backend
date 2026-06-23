package com.adanext.NoPainNoMain.mapper.types;

import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.persistence.types.BookingStatusEntity;

public class BookingStatusMapper {

    public static BookingStatus toDomain(BookingStatusEntity entity) {
        if (entity == null) return null;
        
        return new BookingStatus(
            entity.getId(),
            entity.getName()
        );
    }

    public static BookingStatusEntity toEntity(BookingStatus domain) {
        if (domain == null) return null;
        
        return new BookingStatusEntity(
            domain.getId(),
            domain.getName()
        );
    }
}