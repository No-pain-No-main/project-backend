package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;
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
        if (isNull(booking)) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }

        if (existsById(booking)) {
            throw new IllegalStateException("La reserva con ID " + booking.getId() + " ya existe en el sistema");
        }

        if (hasReachedActiveLimit(booking)) {
            int active = bookingRepository.countActiveByStudent(studentDocumentNumber(booking));
            throw new IllegalStateException(
                "El estudiante " + studentDocumentNumber(booking)
                + " ya tiene " + active + " reservas activas (máximo: " + BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT + ")"
            );
        }

        if (!isValid(booking)) {
            throw new IllegalArgumentException("Datos de reserva inválidos");
        }

        if (isOverleap(booking)) {
            throw new IllegalStateException("El estudiante ya tiene una reserva solapada o la máquina está ocupada en ese intervalo");
        }

        return bookingRepository.save(booking);
    }

    // ─── Helper methods ───────────────────────────────────────────

    private boolean isNull(Booking booking) {
        return booking == null;
    }

    private boolean existsById(Booking booking) {
        return booking.getId() != null && bookingRepository.findById(booking.getId()).isPresent();
    }

    private boolean hasStudentReference(Booking booking) {
        Student student = booking.getStudent();
        return student != null && student.getDocumentNumber() != null;
    }

    private String studentDocumentNumber(Booking booking) {
        return booking.getStudent().getDocumentNumber();
    }

    private boolean hasReachedActiveLimit(Booking booking) {
        return hasStudentReference(booking)
            && bookingRepository.countActiveByStudent(studentDocumentNumber(booking))
               >= BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT;
    }

    boolean isValid(Booking booking) {
        if (isNull(booking)) {
            return false;
        } else if (isOverleap(booking)) {
            return false;
        }
        return true;
    }

    private boolean isOverleap(Booking booking) {
        return bookingQuery.hasOverlappingBooking(booking);
    }
}

