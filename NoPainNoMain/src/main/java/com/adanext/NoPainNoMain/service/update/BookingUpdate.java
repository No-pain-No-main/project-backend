package com.adanext.NoPainNoMain.service.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.domain.repository.BookingStatusRepository;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;

@Service
public class BookingUpdate {

    private final BookingRepository bookingRepository;
    private final BookingStatusRepository bookingStatusRepository;

    public BookingUpdate(BookingRepository bookingRepository,
                         BookingStatusRepository bookingStatusRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingStatusRepository = bookingStatusRepository;
    }

    @Transactional
    public Booking updateStatus(String bookingId, Integer statusId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalStateException("La reserva con ID " + bookingId + " no existe"));

        BookingStatus newStatus = bookingStatusRepository.findById(statusId)
            .orElseThrow(() -> new IllegalStateException("El estado de reserva con ID " + statusId + " no existe"));

        booking.updateStatus(newStatus);
        return bookingRepository.save(booking);
    }
}