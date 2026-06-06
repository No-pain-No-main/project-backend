package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.query.BookingQuery;

@Service
public class BookingRegister {

    private final JsonToClass<Booking> jsonToClass;
    private final BookingRepositoryImpl bookingRepository;
    private final BookingQuery bookingQuery;

    public BookingRegister(JsonToClass<Booking> jsonToClass, BookingRepositoryImpl bookingRepository, BookingQuery bookingQuery) {
        this.jsonToClass = jsonToClass;
        this.bookingRepository = bookingRepository;
        this.bookingQuery = bookingQuery;
    }

    public Booking save(String jsonRegister) {
        Booking booking = jsonToClass.convert(jsonRegister, Booking.class);
        return save(booking);
    }

    public Booking save(Booking booking) {
        // Verificamos si ya existe una reserva con el mismo ID
        if (booking != null && booking.getId() != null 
            && bookingRepository.findById(booking.getId()).isPresent()) {
            throw new IllegalStateException("La reserva con ID " + booking.getId() + " ya existe en el sistema");
        }

        if (!isValid(booking)) {
            throw new IllegalArgumentException("Datos de reserva inválidos");
        }

        if (isOverleap(booking)) {
            throw new IllegalStateException("El estudiante ya tiene una reserva solapada o la máquina está ocupada en ese intervalo");
        }

        return bookingRepository.save(booking);
    }
    boolean isValid(Booking booking) {
        if (booking == null) {
            return false;
        }else if(isOverleap(booking)){
            return false;
        }
        return true;
    }

    private boolean isOverleap(Booking booking) {
        return bookingQuery.hasOverlappingBooking(booking);
    }
}

