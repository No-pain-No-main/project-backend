package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.query.BookingQuery;
import com.adanext.NoPainNoMain.service.update.MachineUpdate;

@Service
public class BookingRegister {

    private final JsonToClass<Booking> jsonToClass;
    private final BookingRepository bookingRepository;
    private final MachineUpdate machineUpdate;
    private final BookingQuery bookingQuery;

    public BookingRegister(JsonToClass<Booking> jsonToClass, BookingRepository bookingRepository,
                           MachineUpdate machineUpdate, BookingQuery bookingQuery) {
        this.jsonToClass = jsonToClass;
        this.bookingRepository = bookingRepository;
        this.machineUpdate = machineUpdate;
        this.bookingQuery = bookingQuery;
    }

    public Booking save(String jsonRegister) {
        Booking booking = jsonToClass.convert(jsonRegister, Booking.class);
        booking.setId(booking.getDate() + "_"
                    + booking.getMachine().getId() + "_"
                    + booking.getTimeSlot().getId());
        return save(booking);
    }

    public Booking save(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }
        boolean isDateBeforeToday = booking.isBeforeToday();
        if (isDateBeforeToday) {
            throw new IllegalStateException("No se puede reservar para una fecha anterior al día de hoy (" + java.time.LocalDate.now() + ")");
        }

        boolean hasSlotAlreadyPassed = booking.hasSlotPassed();
        if (hasSlotAlreadyPassed) {
            throw new IllegalStateException("La franja " + booking.getTimeSlot().getName() + " ya pasó. No se puede reservar para un horario anterior al actual");
        }


        boolean isNotWeekday = !booking.isWeekday();
        if (isNotWeekday) {
            throw new IllegalStateException("Solo se pueden hacer reservas entre semana (lunes a viernes). El " + booking.getDate().getDayOfWeek() + " no está permitido");
        }

        boolean isOutsideCurrentWeek = !booking.isCurrentWeek();
        if (isOutsideCurrentWeek) {
            throw new IllegalStateException("Solo se pueden hacer reservas dentro de la semana actual. La fecha " + booking.getDate() + " está fuera de la semana vigente");
        }

        boolean alreadyExists = booking.getId() != null && bookingRepository.findById(booking.getId()).isPresent();

        if (alreadyExists) {
            throw new IllegalStateException("La reserva con ID " + booking.getId() + " ya existe en el sistema");
        }

        Student student = booking.getStudent();
        if (student != null && student.getDocumentNumber() != null) {
            int active = bookingRepository.countActiveByStudent(student.getDocumentNumber());
            boolean hasTooManyActiveBookings = active >= BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT;
            if (hasTooManyActiveBookings) {
                throw new IllegalStateException(
                    "El estudiante " + student.getDocumentNumber()
                    + " ya tiene " + active + " reservas activas (máximo: " + BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT + ")"
                );
            }
        }

        if (bookingQuery.hasOverlappingBooking(booking)) {
            throw new IllegalStateException("El estudiante ya tiene una reserva solapada o la máquina está ocupada en ese intervalo");
        }

        machineUpdate.updateStatus(booking.getMachine().getId(), BookingParameters.MACHINE_STATUS_RESERVED);

        return bookingRepository.save(booking);
    }
}