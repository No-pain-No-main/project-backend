package com.adanext.NoPainNoMain.service.update.helpers;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.domain.repository.TimeSlotRepository;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BookingConfirmHelper {

  private final BookingRepository bookingRepository;
  private final TimeSlotRepository timeSlotRepository;

  public BookingConfirmHelper(
      BookingRepository bookingRepository,
      TimeSlotRepository timeSlotRepository) {
    this.bookingRepository = bookingRepository;
    this.timeSlotRepository = timeSlotRepository;
  }

  public List<Booking> findTodayActiveBookings(String studentDocumentNumber) {
    LocalDate today = LocalDate.now();
    return bookingRepository.findByStudentDocumentNumber(studentDocumentNumber).stream()
        .filter(b -> isActiveToday(b, today))
        .collect(Collectors.toList());
  }

  public boolean isActiveToday(Booking booking, LocalDate today) {
    return booking != null
        && booking.getDate() != null
        && booking.getDate().equals(today)
        && booking.getBookingStatus() != null
        && booking.getBookingStatus().getId() == BookingParameters.BOOKING_STATUS_ACTIVE;
  }

  public Booking findBookingReadyToStart(List<Booking> bookings) {
    LocalTime currentTime = LocalTime.now();
    for (Booking b : bookings) {
      if (b.getTimeSlot() == null || b.getTimeSlot().getStartTime() == null) {
        continue;
      }
      LocalTime slotStart = b.getTimeSlot().getStartTime();
      LocalTime windowStart = slotStart.minusMinutes(BookingParameters.CONFIRMATION_WINDOW_MINUTES);
      if (!currentTime.isBefore(windowStart) && !currentTime.isAfter(slotStart)) {
        return b;
      }
    }
    return null;
  }

  public void confirmBooking(Booking booking) {
    booking.updateStatus(new BookingStatus(BookingParameters.BOOKING_STATUS_CONFIRMED, null));
  }

  public boolean hasNextBooking(Booking booking) {
    if (booking.getTimeSlot() == null) {
      return false;
    }
    int nextSlotId = booking.getTimeSlot().getId() + 1;
    return timeSlotRepository
        .findById(nextSlotId)
        .map(
            nextSlot ->
                bookingRepository
                    .findByMachineIdAndDateBetween(
                        booking.getMachine().getId(), booking.getDate(), booking.getDate())
                    .stream()
                    .anyMatch(
                        b -> b.getTimeSlot() != null && b.getTimeSlot().getId().equals(nextSlotId)))
        .orElse(false);
  }
}
