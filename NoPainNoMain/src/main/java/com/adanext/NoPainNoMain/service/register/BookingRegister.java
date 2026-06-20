package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.query.BookingQuery;
import com.adanext.NoPainNoMain.service.update.MachineUpdate;

@Service
public class BookingRegister {

    private final JsonToClass<Booking> jsonToClass;
    private final BookingRepositoryImpl bookingRepository;
    private final MachineUpdate machineUpdate;
    private final BookingQuery bookingQuery;

    public BookingRegister(JsonToClass<Booking> jsonToClass, BookingRepositoryImpl bookingRepository,
                           MachineUpdate machineUpdate, BookingQuery bookingQuery) {
        this.jsonToClass = jsonToClass;
        this.bookingRepository = bookingRepository;
        this.machineUpdate = machineUpdate;
        this.bookingQuery = bookingQuery;
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
        if (booking == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }

        // Validaciones de dominio → la entidad las sabe hacer
        if (booking.isBeforeToday()) {
            throw new IllegalStateException("No se puede reservar para una fecha anterior al día de hoy (" + java.time.LocalDate.now() + ")");
        }

        if (booking.hasSlotPassed()) {
            throw new IllegalStateException("La franja " + booking.getTimeSlot().getName() + " ya pasó. No se puede reservar para un horario anterior al actual");
        }

        if (!booking.isWeekday()) {
            throw new IllegalStateException("Solo se pueden hacer reservas entre semana (lunes a viernes). El " + booking.getDate().getDayOfWeek() + " no está permitido");
        }

        if (!booking.isCurrentWeek()) {
            throw new IllegalStateException("Solo se pueden hacer reservas dentro de la semana actual. La fecha " + booking.getDate() + " está fuera de la semana vigente");
        }

        // Validaciones que requieren repositorio → se quedan en el Service
        if (booking.getId() != null && bookingRepository.findById(booking.getId()).isPresent()) {
            throw new IllegalStateException("La reserva con ID " + booking.getId() + " ya existe en el sistema");
        }

        Student student = booking.getStudent();
        if (student != null && student.getDocumentNumber() != null) {
            int active = bookingRepository.countActiveByStudent(student.getDocumentNumber());
            if (active >= BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT) {
                throw new IllegalStateException(
                    "El estudiante " + student.getDocumentNumber()
                    + " ya tiene " + active + " reservas activas (máximo: " + BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT + ")"
                );
            }
        }

        if (bookingQuery.hasOverlappingBooking(booking)) {
            throw new IllegalStateException("El estudiante ya tiene una reserva solapada o la máquina está ocupada en ese intervalo");
        }

        // Cambiar estado de la máquina a "Reservada"
        machineUpdate.updateStatus(booking.getMachine().getId(), BookingParameters.MACHINE_STATUS_RESERVED);

        return bookingRepository.save(booking);
    }
}