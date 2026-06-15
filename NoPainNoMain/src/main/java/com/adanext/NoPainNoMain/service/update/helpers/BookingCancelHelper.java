package com.adanext.NoPainNoMain.service.update.helpers;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.persistence.impl.BookingStatusRepositoryImpl;
import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class BookingCancelHelper {

  private final BookingStatusRepositoryImpl bookingStatusRepository;

  private Clock clock = Clock.systemDefaultZone();

  public BookingCancelHelper(BookingStatusRepositoryImpl bookingStatusRepository) {
    this.bookingStatusRepository = bookingStatusRepository;
  }

  public void setClock(Clock clock) {
    this.clock = clock;
  }

  public boolean canBeCancelled(Booking booking) {
    LocalDateTime slotStart =
        LocalDateTime.of(booking.getDate(), booking.getTimeSlot().getStartTime());
    return !LocalDateTime.now(clock)
        .isAfter(slotStart.minusMinutes(BookingParameters.CANCELLATION_MINUTES_BEFORE));
  }

  public void cancelBooking(Booking booking) {
    BookingStatus cancelled =
        bookingStatusRepository
            .findById(BookingParameters.BOOKING_STATUS_CANCELLED)
            .orElseThrow(
                () -> new IllegalStateException("El estado 'Cancelada' no existe en el sistema"));
    booking.updateStatus(cancelled);
  }
}
