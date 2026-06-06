package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.TimeSlotRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.query.BookingQuery;

@Service
public class BookingRegister {

    private final JsonToClass<Booking> jsonToClass;
    private final BookingRepositoryImpl bookingRepository;
    private final BookingQuery bookingQuery;
    private final TimeSlotRepositoryImpl timeSlotRepository;

    public BookingRegister(JsonToClass<Booking> jsonToClass, BookingRepositoryImpl bookingRepository,
                           BookingQuery bookingQuery, TimeSlotRepositoryImpl timeSlotRepository) {
        this.jsonToClass = jsonToClass;
        this.bookingRepository = bookingRepository;
        this.bookingQuery = bookingQuery;
        this.timeSlotRepository = timeSlotRepository;
    }

    public Booking save(String jsonRegister) {
        Booking booking = jsonToClass.convert(jsonRegister, Booking.class);
        // Auto-generar id: date_machineId_slotId
        booking.setId(booking.getDate() + "_"
                    + booking.getMachine().getId() + "_"
                    + booking.getTimeSlot().getId());
        return save(booking);
    }

    public Booking save(Booking booking) {
        if (isNull(booking)) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }

        if (isBeforeToday(booking)) {
            throw new IllegalStateException("No se puede reservar para una fecha anterior al día de hoy (" + java.time.LocalDate.now() + ")");
        }

        if (isSlotAlreadyPassed(booking)) {
            throw new IllegalStateException("La franja " + booking.getTimeSlot().getName() + " ya pasó. No se puede reservar para un horario anterior al actual");
        }

        if (!isWeekday(booking)) {
            throw new IllegalStateException("Solo se pueden hacer reservas entre semana (lunes a viernes). El " + booking.getDate().getDayOfWeek() + " no está permitido");
        }

        if (!isCurrentWeek(booking)) {
            throw new IllegalStateException("Solo se pueden hacer reservas dentro de la semana actual. La fecha " + booking.getDate() + " está fuera de la semana vigente");
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

        if (isOverleap(booking)) {
            throw new IllegalStateException("El estudiante ya tiene una reserva solapada o la máquina está ocupada en ese intervalo");
        }

        if (booking == null) {
            throw new IllegalArgumentException("Datos de reserva inválidos");
        }

        return bookingRepository.save(booking);
    }

    // ─── Helper methods ───────────────────────────────────────────

    private boolean isNull(Booking booking) {
        return booking == null;
    }

    private boolean isBeforeToday(Booking booking) {
        return booking.getDate() != null && booking.getDate().isBefore(java.time.LocalDate.now());
    }

    private boolean isWeekday(Booking booking) {
        if (booking.getDate() == null) return false;
        java.time.DayOfWeek day = booking.getDate().getDayOfWeek();
        return day != java.time.DayOfWeek.SATURDAY && day != java.time.DayOfWeek.SUNDAY;
    }

    private boolean isCurrentWeek(Booking booking) {
        if (booking.getDate() == null) return false;
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate monday = today.with(java.time.DayOfWeek.MONDAY);
        java.time.LocalDate sunday = today.with(java.time.DayOfWeek.SUNDAY);
        return !booking.getDate().isBefore(monday) && !booking.getDate().isAfter(sunday);
    }

    private boolean isSlotAlreadyPassed(Booking booking) {
        if (booking.getDate() == null || booking.getTimeSlot() == null) {
            return false;
        }

        // Resolver startTime desde BD si el JSON no lo trajo
        TimeSlot resolved = booking.getTimeSlot();
        if (resolved.getStartTime() == null && resolved.getId() != null) {
            resolved = timeSlotRepository.findById(resolved.getId()).orElse(resolved);
        }
        if (resolved.getStartTime() == null) {
            return false;
        }

        java.time.LocalDateTime slotStart = java.time.LocalDateTime.of(booking.getDate(), resolved.getStartTime());
        return java.time.LocalDateTime.now().isAfter(slotStart);
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

    private boolean isOverleap(Booking booking) {
        return bookingQuery.hasOverlappingBooking(booking);
    }
}

