package com.adanext.NoPainNoMain.service.update;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.BookingStatusRepositoryImpl;

@Service
public class BookingCancelService {

    private final BookingRepositoryImpl bookingRepository;
    private final BookingStatusRepositoryImpl bookingStatusRepository;
    private final MachineUpdate machineUpdate;

    public BookingCancelService(BookingRepositoryImpl bookingRepository,
                                BookingStatusRepositoryImpl bookingStatusRepository,
                                MachineUpdate machineUpdate) {
        this.bookingRepository = bookingRepository;
        this.bookingStatusRepository = bookingStatusRepository;
        this.machineUpdate = machineUpdate;
    }

    public Booking cancel(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalStateException("La reserva con ID " + bookingId + " no existe"));

        // Comportamiento del dominio: la entidad sabe si puede cancelarse
        if (!booking.canBeCancelled(BookingParameters.CANCELLATION_MINUTES_BEFORE)) {
            throw new IllegalStateException(
                "No se puede cancelar la reserva. Deben faltar al menos " + BookingParameters.CANCELLATION_MINUTES_BEFORE
                + " minutos para el inicio de la franja ("
                + booking.getDate() + " " + booking.getTimeSlot().getName() + ")"
            );
        }

        // Liberar la máquina antes de cancelar
        machineUpdate.updateStatus(booking.getMachine().getId(), BookingParameters.MACHINE_STATUS_AVAILABLE);

        // Comportamiento del dominio: cancelar la reserva
        BookingStatus cancelled = bookingStatusRepository.findById(BookingParameters.BOOKING_STATUS_CANCELLED)
            .orElseThrow(() -> new IllegalStateException("El estado 'Cancelada' no existe en el sistema"));
        booking.cancel(cancelled);

        return bookingRepository.save(booking);
    }
}