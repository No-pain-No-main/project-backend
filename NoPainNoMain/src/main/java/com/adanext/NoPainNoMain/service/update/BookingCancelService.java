package com.adanext.NoPainNoMain.service.update;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.service.update.helpers.BookingCancelHelper;

@Service
public class BookingCancelService {

    private final BookingRepositoryImpl bookingRepository;
    private final BookingCancelHelper helper;
    private final MachineUpdate machineUpdate;

    public BookingCancelService(BookingRepositoryImpl bookingRepository,
                                BookingCancelHelper helper,
                                MachineUpdate machineUpdate) {
        this.bookingRepository = bookingRepository;
        this.helper = helper;
        this.machineUpdate = machineUpdate;
    }

    public Booking cancel(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalStateException("La reserva con ID " + bookingId + " no existe"));

        if (!helper.canBeCancelled(booking)) {
            throw new IllegalStateException(
                "No se puede cancelar la reserva. Deben faltar al menos " + BookingParameters.CANCELLATION_MINUTES_BEFORE
                + " minutos para el inicio de la franja ("
                + booking.getDate() + " " + booking.getTimeSlot().getName() + ")"
            );
        }

        // Liberar la máquina antes de cancelar
        machineUpdate.updateStatus(booking.getMachine().getId(), BookingParameters.MACHINE_STATUS_AVAILABLE);

        helper.cancelBooking(booking);
        return bookingRepository.save(booking);
    }
}
