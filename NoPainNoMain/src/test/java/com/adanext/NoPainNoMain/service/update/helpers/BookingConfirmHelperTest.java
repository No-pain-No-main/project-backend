package com.adanext.NoPainNoMain.service.update.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.domain.repository.TimeSlotRepository;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.persistence.entities.BookingEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingConfirmHelperTest {

  @Mock private BookingRepository bookingRepository;

  @Mock private TimeSlotRepository timeSlotRepository;

  @InjectMocks private BookingConfirmHelper helper;

  private static final String STUDENT_DOC = "55667788";
  private LocalDate TODAY;

  private Booking activeBookingToday;
  private BookingEntity bookingEntity;
  private TimeSlot slot;
  private Machine machine;

  @BeforeEach
  void setUp() {
    LocalDateTime baseTime = LocalDateTime.now();
    TODAY = baseTime.toLocalDate();

    machine = new Machine();
    machine.setId(1);

    slot = new TimeSlot();
    slot.setId(3);
    LocalDateTime slotTime =
        baseTime.plusMinutes(BookingParameters.CONFIRMATION_WINDOW_MINUTES - 1);
    slot.setStartTime(slotTime.toLocalTime());

    BookingStatus activeStatus = new BookingStatus(BookingParameters.BOOKING_STATUS_ACTIVE, null);

    activeBookingToday = new Booking();
    activeBookingToday.setId("booking-1");
    activeBookingToday.setDate(slotTime.toLocalDate());
    activeBookingToday.setTimeSlot(slot);
    activeBookingToday.setMachine(machine);
    activeBookingToday.updateStatus(activeStatus);

    bookingEntity = new BookingEntity();
    bookingEntity.setId("booking-1");
  }

  // ─── findTodayActiveBookings ──────────────────────────────────────────────

  @Test
  void findTodayActiveBookings_whenStudentHasActiveTodayBooking_returnsIt() {
    when(bookingRepository.findByStudentDocumentNumber(STUDENT_DOC))
        .thenReturn(Collections.singletonList(activeBookingToday));

    List<Booking> result = helper.findTodayActiveBookings(STUDENT_DOC);

    assertEquals(1, result.size());
    assertEquals("booking-1", result.get(0).getId());
  }

  @Test
  void findTodayActiveBookings_whenStudentHasNoBookings_returnsEmptyList() {
    when(bookingRepository.findByStudentDocumentNumber(STUDENT_DOC))
        .thenReturn(Collections.emptyList());

    List<Booking> result = helper.findTodayActiveBookings(STUDENT_DOC);

    assertTrue(result.isEmpty());
  }

  @Test
  void findTodayActiveBookings_whenBookingIsForAnotherDay_excludesIt() {
    Booking bookingYesterday = new Booking();
    bookingYesterday.setId("booking-2");
    bookingYesterday.setDate(TODAY.minusDays(1));
    bookingYesterday.setTimeSlot(slot);
    bookingYesterday.updateStatus(new BookingStatus(BookingParameters.BOOKING_STATUS_ACTIVE, null));

    when(bookingRepository.findByStudentDocumentNumber(STUDENT_DOC))
        .thenReturn(Collections.singletonList(bookingYesterday));

    List<Booking> result = helper.findTodayActiveBookings(STUDENT_DOC);

    assertTrue(result.isEmpty());
  }

  @Test
  void findTodayActiveBookings_whenBookingIsNotActive_excludesIt() {
    Booking cancelledBooking = new Booking();
    cancelledBooking.setId("booking-3");
    cancelledBooking.setDate(TODAY);
    cancelledBooking.setTimeSlot(slot);
    cancelledBooking.updateStatus(
        new BookingStatus(BookingParameters.BOOKING_STATUS_CANCELLED, null));

    when(bookingRepository.findByStudentDocumentNumber(STUDENT_DOC))
        .thenReturn(Collections.singletonList(cancelledBooking));

    List<Booking> result = helper.findTodayActiveBookings(STUDENT_DOC);

    assertTrue(result.isEmpty());
  }

  // ─── findBookingReadyToStart ──────────────────────────────────────────────

  @Test
  void findBookingReadyToStart_whenBookingIsWithinConfirmationWindow_returnsIt() {
    LocalDateTime targetTime =
        LocalDateTime.now().plusMinutes(BookingParameters.CONFIRMATION_WINDOW_MINUTES - 1);
    slot.setStartTime(targetTime.toLocalTime());
    activeBookingToday.setDate(targetTime.toLocalDate());

    Booking result = helper.findBookingReadyToStart(Collections.singletonList(activeBookingToday));

    assertNotNull(result);
    assertEquals("booking-1", result.getId());
  }

  @Test
  void findBookingReadyToStart_whenBookingStartsNow_returnsIt() {
    LocalDateTime targetTime = LocalDateTime.now();
    slot.setStartTime(targetTime.toLocalTime());
    activeBookingToday.setDate(targetTime.toLocalDate());

    Booking result = helper.findBookingReadyToStart(Collections.singletonList(activeBookingToday));

    assertNotNull(result);
  }

  @Test
  void findBookingReadyToStart_whenBookingIsTooFarInFuture_returnsNull() {
    LocalDateTime targetTime =
        LocalDateTime.now().plusMinutes(BookingParameters.CONFIRMATION_WINDOW_MINUTES + 30);
    slot.setStartTime(targetTime.toLocalTime());
    activeBookingToday.setDate(targetTime.toLocalDate());

    Booking result = helper.findBookingReadyToStart(Collections.singletonList(activeBookingToday));

    assertNull(result);
  }

  @Test
  void findBookingReadyToStart_whenSlotAlreadyStarted_returnsNull() {
    LocalDateTime targetTime = LocalDateTime.now().minusMinutes(5);
    slot.setStartTime(targetTime.toLocalTime());
    activeBookingToday.setDate(targetTime.toLocalDate());

    Booking result = helper.findBookingReadyToStart(Collections.singletonList(activeBookingToday));

    assertNull(result);
  }

  @Test
  void findBookingReadyToStart_whenEmptyList_returnsNull() {
    Booking result = helper.findBookingReadyToStart(Collections.emptyList());
    assertNull(result);
  }

  @Test
  void findBookingReadyToStart_whenMultipleBookingsOnlyOneInWindow_returnsCorrectOne() {
    LocalDateTime futureTime = LocalDateTime.now().plusHours(3);
    TimeSlot futureSlot = new TimeSlot();
    futureSlot.setId(7);
    futureSlot.setStartTime(futureTime.toLocalTime());

    Booking futureBooking = new Booking();
    futureBooking.setId("booking-future");
    futureBooking.setTimeSlot(futureSlot);
    futureBooking.setDate(futureTime.toLocalDate());

    LocalDateTime targetTime =
        LocalDateTime.now().plusMinutes(BookingParameters.CONFIRMATION_WINDOW_MINUTES - 2);
    slot.setStartTime(targetTime.toLocalTime());
    activeBookingToday.setDate(targetTime.toLocalDate());

    Booking result =
        helper.findBookingReadyToStart(Arrays.asList(futureBooking, activeBookingToday));

    assertNotNull(result);
    assertEquals("booking-1", result.getId());
  }

  // ─── hasNextBooking ───────────────────────────────────────────────────────

  @Test
  void hasNextBooking_whenNextSlotExistsAndIsBooked_returnsTrue() {
    TimeSlot nextSlot = new TimeSlot();
    nextSlot.setId(4);

    Booking nextBooking= new Booking();
    TimeSlot nextSlotRef = new TimeSlot();
    nextSlotRef.setId(4);
    nextBooking.setTimeSlot(nextSlotRef);

    when(timeSlotRepository.findById(4)).thenReturn(Optional.of(nextSlot));
    when(bookingRepository.findByMachineIdAndDateBetween(eq(1), eq(TODAY), eq(TODAY)))
        .thenReturn(Collections.singletonList(nextBooking));

    boolean result = helper.hasNextBooking(activeBookingToday);

    assertTrue(result);
  }

  @Test
  void hasNextBooking_whenNextSlotDoesNotExist_returnsFalse() {
    when(timeSlotRepository.findById(4)).thenReturn(Optional.empty());

    boolean result = helper.hasNextBooking(activeBookingToday);

    assertFalse(result);
  }

  @Test
  void hasNextBooking_whenNextSlotExistsButNotBooked_returnsFalse() {
    TimeSlot nextSlot = new TimeSlot();
    nextSlot.setId(4);

    when(timeSlotRepository.findById(4)).thenReturn(Optional.of(nextSlot));
    when(bookingRepository.findByMachineIdAndDateBetween(eq(1), eq(TODAY), eq(TODAY)))
        .thenReturn(Collections.emptyList());

    boolean result = helper.hasNextBooking(activeBookingToday);

    assertFalse(result);
  }

  @Test
  void hasNextBooking_whenTimeSlotIsNull_returnsFalse() {
    activeBookingToday.setTimeSlot(null);

    boolean result = helper.hasNextBooking(activeBookingToday);

    assertFalse(result);
  }
}
