package com.adanext.NoPainNoMain.service.register.helpers;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.domain.repository.TimeSlotRepository;
import com.adanext.NoPainNoMain.service.query.BookingQuery;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class BookingRegisterHelper {

  private final BookingRepository bookingRepository;
  private final BookingQuery bookingQuery;
  private final TimeSlotRepository timeSlotRepository;
  private Clock clock = Clock.systemDefaultZone();

  public void setClock(Clock clock) {
    this.clock = clock;
  }

  public BookingRegisterHelper(
      BookingRepository bookingRepository,
      BookingQuery bookingQuery,
      TimeSlotRepository timeSlotRepository) {
    this.bookingRepository = bookingRepository;
    this.bookingQuery = bookingQuery;
    this.timeSlotRepository = timeSlotRepository;
  }

  public boolean isNull(Booking booking) {
    return booking == null;
  }

  public boolean isBeforeToday(Booking booking) {
    return booking.getDate() != null && booking.getDate().isBefore(LocalDate.now(clock));
  }

  public boolean isWeekday(Booking booking) {
    if (booking.getDate() == null) {
      return false;
    }
    DayOfWeek day = booking.getDate().getDayOfWeek();
    return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
  }

  public boolean isCurrentWeek(Booking booking) {
    if (booking.getDate() == null) {
      return false;
    }
    LocalDate today = LocalDate.now(clock);
    LocalDate monday = today.with(DayOfWeek.MONDAY);
    LocalDate sunday = today.with(DayOfWeek.SUNDAY);
    return !booking.getDate().isBefore(monday) && !booking.getDate().isAfter(sunday);
  }

  public boolean isSlotAlreadyPassed(Booking booking) {
    if (booking.getDate() == null || booking.getTimeSlot() == null) {
      return false;
    }

    TimeSlot resolved = booking.getTimeSlot();
    if (resolved.getStartTime() == null && resolved.getId() != null) {
      resolved = timeSlotRepository.findById(resolved.getId()).orElse(resolved);
    }
    if (resolved.getStartTime() == null) {
      return false;
    }

    LocalDateTime slotStart = LocalDateTime.of(booking.getDate(), resolved.getStartTime());
    return LocalDateTime.now(clock).isAfter(slotStart);
  }

  public boolean existsById(Booking booking) {
    return booking.getId() != null && bookingRepository.findById(booking.getId()).isPresent();
  }

  public boolean hasStudentReference(Booking booking) {
    Student student = booking.getStudent();
    return student != null && student.getDocumentNumber() != null;
  }

  public String studentDocumentNumber(Booking booking) {
    return booking.getStudent().getDocumentNumber();
  }

  public boolean hasReachedActiveLimit(Booking booking) {
    return hasStudentReference(booking)
        && bookingRepository.countActiveByStudent(studentDocumentNumber(booking))
            >= BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT;
  }

  public boolean isOverleap(Booking booking) {
    return bookingQuery.hasOverlappingBooking(booking);
  }
}
