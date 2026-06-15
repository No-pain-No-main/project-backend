package com.adanext.NoPainNoMain.service.query;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingQueryTest {

  @Mock private BookingRepositoryImpl bookingRepository;

  @InjectMocks private BookingQuery bookingQuery;

  private Student student;
  private TimeSlot slot;
  private LocalDate date;

  @BeforeEach
  void setUp() {
    student = new Student();
    student.setDocumentNumber("12345678");

    slot = new TimeSlot();
    slot.setId(3);

    date = LocalDate.now().plusDays(1);
  }

  @Test
  void hasOverlappingBooking_whenNullBooking_returnsFalse() {
    boolean result = bookingQuery.hasOverlappingBooking(null);
    assertFalse(result);
  }

  @Test
  void hasOverlappingBooking_whenNoExistingBookingsForStudent_returnsFalse() {
    Booking newBooking = new Booking();
    newBooking.setStudent(student);
    newBooking.setDate(date);
    newBooking.setTimeSlot(slot);

    when(bookingRepository.findByStudent(student)).thenReturn(Collections.emptyList());

    boolean result = bookingQuery.hasOverlappingBooking(newBooking);

    assertFalse(result);
  }

  @Test
  void hasOverlappingBooking_whenExactSameDateAndSlot_returnsTrue() {
    Booking existingBooking = new Booking();
    existingBooking.setStudent(student);
    existingBooking.setDate(date);
    existingBooking.setTimeSlot(slot);

    Booking newBooking = new Booking();
    newBooking.setStudent(student);
    newBooking.setDate(date);
    newBooking.setTimeSlot(slot);

    when(bookingRepository.findByStudent(student))
        .thenReturn(Collections.singletonList(existingBooking));

    boolean result = bookingQuery.hasOverlappingBooking(newBooking);

    assertTrue(result);
  }

  @Test
  void hasOverlappingBooking_whenSameDateButDifferentSlot_returnsFalse() {
    TimeSlot otherSlot = new TimeSlot();
    otherSlot.setId(5);

    Booking existingBooking = new Booking();
    existingBooking.setStudent(student);
    existingBooking.setDate(date);
    existingBooking.setTimeSlot(otherSlot);

    Booking newBooking = new Booking();
    newBooking.setStudent(student);
    newBooking.setDate(date);
    newBooking.setTimeSlot(slot);

    when(bookingRepository.findByStudent(student))
        .thenReturn(Collections.singletonList(existingBooking));

    boolean result = bookingQuery.hasOverlappingBooking(newBooking);

    assertFalse(result);
  }

  @Test
  void hasOverlappingBooking_whenSameSlotButDifferentDate_returnsFalse() {
    Booking existingBooking = new Booking();
    existingBooking.setStudent(student);
    existingBooking.setDate(date.plusDays(1));
    existingBooking.setTimeSlot(slot);

    Booking newBooking = new Booking();
    newBooking.setStudent(student);
    newBooking.setDate(date);
    newBooking.setTimeSlot(slot);

    when(bookingRepository.findByStudent(student))
        .thenReturn(Collections.singletonList(existingBooking));

    boolean result = bookingQuery.hasOverlappingBooking(newBooking);

    assertFalse(result);
  }

  @Test
  void hasOverlappingBooking_whenStudentHasMultipleBookingsAndOneMatches_returnsTrue() {
    TimeSlot slotA = new TimeSlot();
    slotA.setId(1);
    TimeSlot slotB = new TimeSlot();
    slotB.setId(2);

    Booking b1 = new Booking();
    b1.setStudent(student);
    b1.setDate(date.minusDays(1));
    b1.setTimeSlot(slotA);
    Booking b2 = new Booking();
    b2.setStudent(student);
    b2.setDate(date);
    b2.setTimeSlot(slot);

    Booking newBooking = new Booking();
    newBooking.setStudent(student);
    newBooking.setDate(date);
    newBooking.setTimeSlot(slot);

    when(bookingRepository.findByStudent(student)).thenReturn(Arrays.asList(b1, b2));

    boolean result = bookingQuery.hasOverlappingBooking(newBooking);

    assertTrue(result);
  }
}
