package com.adanext.NoPainNoMain.service.update;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.service.query.TodayBookingsQuery;

@Service
public class BookingConfirmService {

    private final BookingRepository bookingRepository;
    private final TodayBookingsQuery todayBookingsQuery;

    public BookingConfirmService(BookingRepository bookingRepository,
                                  TodayBookingsQuery todayBookingsQuery) {
        this.bookingRepository = bookingRepository;
        this.todayBookingsQuery = todayBookingsQuery;
    }

    public Booking confirm(String studentDocumentNumber) {
        List<Booking> todayActive = todayBookingsQuery.findTodayActiveBookings(studentDocumentNumber);

        if (todayActive.isEmpty()) {
            throw new IllegalStateException(
                "El estudiante " + studentDocumentNumber + " no tiene reservas activas para hoy"
            );
        }

        Booking bookingToConfirm = findBookingReadyToStart(todayActive);

        if (bookingToConfirm == null) {
            throw new IllegalStateException(
                "No hay ninguna reserva próxima a confirmar. Solo se pueden confirmar reservas dentro de los "
                + BookingParameters.CONFIRMATION_WINDOW_MINUTES
                + " minutos antes de su inicio"
            );
        }

        bookingToConfirm.confirm(
            new BookingStatus(BookingParameters.BOOKING_STATUS_CONFIRMED, null)
        );
        return bookingRepository.save(bookingToConfirm);
    }

    private Booking findBookingReadyToStart(List<Booking> bookings) {
        for (Booking b : bookings) {
            if (b.isReadyForConfirmation(BookingParameters.CONFIRMATION_WINDOW_MINUTES)) {
                return b;
            }
        }
        return null;
    }
}