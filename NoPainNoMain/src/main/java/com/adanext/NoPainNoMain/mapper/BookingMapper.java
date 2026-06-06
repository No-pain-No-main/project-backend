package com.adanext.NoPainNoMain.mapper;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.mapper.types.BookingStatusMapper;
import com.adanext.NoPainNoMain.persistence.entities.BookingEntity;

public class BookingMapper {

    public static Booking toDomain(BookingEntity entity) {
        if (entity == null) return null;


        return new Booking(
            entity.getId(),
            StudentMapper.toDomain(entity.getStudent()),
            MachineMapper.toDomain(entity.getMachine()),
            entity.getDate(),
            TimeSlotMapper.toDomain(entity.getTimeSlot()),
            BookingStatusMapper.toDomain(entity.getBookingStatus())
        );
    }

    public static BookingEntity toEntity(Booking domain) {
        if (domain == null) return null;

        BookingEntity entity = new BookingEntity();
        entity.setId(domain.getId());
        entity.setStudent(StudentMapper.toEntity(domain.getStudent()));
        entity.setMachine(MachineMapper.toEntity(domain.getMachine()));
        entity.setDate(domain.getDate());
        entity.setTimeSlot(TimeSlotMapper.toEntity(domain.getTimeSlot()));
        entity.setBookingStatus(BookingStatusMapper.toEntity(domain.getBookingStatus()));
        
        return entity;
    }
}