package com.adanext.NoPainNoMain.service.update;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.BookingStatusRepositoryImpl;

@Service
public class BookingCancelService {

    private final BookingRepositoryImpl bookingRepository;
    private final BookingStatusRepositoryImpl bookingStatusRepository;

    public BookingCancelService(BookingRepositoryImpl bookingRepository,
                                BookingStatusRepositoryImpl bookingStatusRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingStatusRepository = bookingStatusRepository;
    }

    public Booking cancel(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalStateException("La reserva con ID " + bookingId + " no existe"));

        // Calcular el inicio de la franja: date + startTime del slot
        LocalDateTime slotStart = LocalDateTime.of(booking.getDate(), booking.getTimeSlot().getStartTime());
        LocalDateTime now = LocalDateTime.now();

        // Validar que falten al menos 30 minutos para el inicio
        if (now.isAfter(slotStart.minusMinutes(30))) {
            throw new IllegalStateException(
                "No se puede cancelar la reserva. Deben faltar al menos 30 minutos para el inicio de la franja ("
                + booking.getDate() + " " + booking.getTimeSlot().getName() + ")"
            );
        }

        // Cambiar estado a Cancelada (id = 2)
        BookingStatus cancelled = bookingStatusRepository.findById(2)
            .orElseThrow(() -> new IllegalStateException("El estado 'Cancelada' no existe en el sistema"));

        booking.updateStatus(cancelled);
        return bookingRepository.save(booking);
    }
}
