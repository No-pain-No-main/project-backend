package com.adanext.NoPainNoMain.service.update;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.adanext.NoPainNoMain.persistence.entities.BookingEntity;
import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.service.update.helpers.BookingConfirmHelper;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingConfirmServiceTest {

  @Mock private BookingRepository bookingRepository;

  @Mock private MachineUpdate machineUpdate;

  @Mock private BookingConfirmHelper helper;

  @InjectMocks private BookingConfirmService confirmService;

  private static final String STUDENT_DOC = "44332211";
  private static final LocalDate TODAY = LocalDate.of(2023, 10, 10);

  private Booking activeBooking;
  private Machine machine;
  private TimeSlot slot;

  @BeforeEach
  void setUp() {
    Clock fixedClock = Clock.fixed(Instant.parse("2023-10-10T15:00:00Z"), ZoneId.of("UTC"));

    confirmService.setClock(fixedClock);

    machine = new Machine();
    machine.setId(2);

    slot = new TimeSlot();
    slot.setId(4);
    slot.setStartTime(LocalTime.of(16, 0));

    BookingStatus activeStatus = new BookingStatus(BookingParameters.BOOKING_STATUS_ACTIVE, null);

    activeBooking = new Booking();
    activeBooking.setId("booking-active");

    activeBooking.setDate(TODAY);
    activeBooking.setMachine(machine);
    activeBooking.setTimeSlot(slot);
    activeBooking.updateStatus(activeStatus);
  }

  // ─── confirm ─────────────────────────────────────────────────────────────

  @Test
  void confirm_studentWithActiveBookingReadyToStart_confirmsSuccessfully() {
    when(helper.findTodayActiveBookings(STUDENT_DOC))
        .thenReturn(Collections.singletonList(activeBooking));
    when(helper.findBookingReadyToStart(any())).thenReturn(activeBooking);
    when(bookingRepository.save(activeBooking)).thenReturn(activeBooking);

    Booking result = confirmService.confirm(STUDENT_DOC);

    assertNotNull(result);
    verify(helper).confirmBooking(activeBooking);
    verify(bookingRepository).save(activeBooking);
  }

  @Test
  void confirm_studentWithNoActiveBookingsToday_throwsIllegalStateException() {
    when(helper.findTodayActiveBookings(STUDENT_DOC)).thenReturn(Collections.emptyList());

    IllegalStateException ex =
        assertThrows(IllegalStateException.class, () -> confirmService.confirm(STUDENT_DOC));

    org.junit.jupiter.api.Assertions.assertTrue(ex.getMessage().contains(STUDENT_DOC));
    verify(helper, never()).confirmBooking(any());
    verify(bookingRepository, never()).save(any());
  }

  @Test
  void confirm_studentHasActiveBookingButNotYetInConfirmationWindow_throwsIllegalStateException() {
    when(helper.findTodayActiveBookings(STUDENT_DOC))
        .thenReturn(Collections.singletonList(activeBooking));
    when(helper.findBookingReadyToStart(any())).thenReturn(null);

    IllegalStateException ex =
        assertThrows(IllegalStateException.class, () -> confirmService.confirm(STUDENT_DOC));

    org.junit.jupiter.api.Assertions.assertTrue(
        ex.getMessage().contains(String.valueOf(BookingParameters.CONFIRMATION_WINDOW_MINUTES)));
    verify(helper, never()).confirmBooking(any());
    verify(bookingRepository, never()).save(any());
  }

  @Test
  void confirm_confirmsBookingByCallingHelperAndSaving() {
    when(helper.findTodayActiveBookings(STUDENT_DOC))
        .thenReturn(Collections.singletonList(activeBooking));
    when(helper.findBookingReadyToStart(any())).thenReturn(activeBooking);
    when(bookingRepository.save(activeBooking)).thenReturn(activeBooking);

    confirmService.confirm(STUDENT_DOC);

    org.mockito.InOrder inOrder = org.mockito.Mockito.inOrder(helper, bookingRepository);
    inOrder.verify(helper).confirmBooking(activeBooking);
    inOrder.verify(bookingRepository).save(activeBooking);
  }

  // ─── releaseExpiredSlots ──────────────────────────────────────────────────

  @Test
  void releaseExpiredSlots_whenNoBookingsToday_doesNothing() {
    when(bookingRepository.findByDate(TODAY)).thenReturn(Collections.emptyList());

    confirmService.releaseExpiredSlots();

    verify(machineUpdate, never()).updateStatus(any(), any());
  }

  @Test
  void releaseExpiredSlots_confirmedBookingWithExpiredSlotAndNoNextBooking_releasesMachine() {
    BookingStatus confirmedStatus =
        new BookingStatus(BookingParameters.BOOKING_STATUS_CONFIRMED, null);
    activeBooking.updateStatus(confirmedStatus);
    slot.setStartTime(LocalTime.of(13, 0));
    activeBooking.setTimeSlot(slot);

    BookingEntity entity = new BookingEntity();
    entity.setId("booking-active");

    when(bookingRepository.findByDate(TODAY)).thenReturn(Collections.singletonList(activeBooking));
    when(helper.hasNextBooking(activeBooking)).thenReturn(false);

    confirmService.releaseExpiredSlots();

    verify(machineUpdate).updateStatus(machine.getId(), BookingParameters.MACHINE_STATUS_AVAILABLE);
  }

  @Test
  void
      releaseExpiredSlots_confirmedBookingWithExpiredSlotButHasNextBooking_doesNotReleaseMachine() {
    BookingStatus confirmedStatus =
        new BookingStatus(BookingParameters.BOOKING_STATUS_CONFIRMED, null);
    activeBooking.updateStatus(confirmedStatus);
    slot.setStartTime(LocalTime.of(13, 0));
    activeBooking.setTimeSlot(slot);

    BookingEntity entity = new BookingEntity();
    entity.setId("booking-active");

    when(bookingRepository.findByDate(TODAY)).thenReturn(Collections.singletonList(activeBooking));
    when(helper.hasNextBooking(activeBooking)).thenReturn(true);

    confirmService.releaseExpiredSlots();

    verify(machineUpdate, never()).updateStatus(any(), any());
  }

  @Test
  void releaseExpiredSlots_activeBookingWithExpiredSlot_doesNotReleaseMachine() {
    slot.setStartTime(LocalTime.of(13, 0));
    activeBooking.setTimeSlot(slot);

    when(bookingRepository.findByDate(TODAY)).thenReturn(Collections.singletonList(activeBooking));

    confirmService.releaseExpiredSlots();

    verify(machineUpdate, never()).updateStatus(any(), any());
  }

  @Test
  void releaseExpiredSlots_confirmedBookingWhoseSlotHasNotYetEnded_doesNotReleaseMachine() {
    BookingStatus confirmedStatus =
        new BookingStatus(BookingParameters.BOOKING_STATUS_CONFIRMED, null);
    activeBooking.updateStatus(confirmedStatus);
    slot.setStartTime(LocalTime.of(14, 30));
    activeBooking.setTimeSlot(slot);

    when(bookingRepository.findByDate(TODAY)).thenReturn(Collections.singletonList(activeBooking));

    confirmService.releaseExpiredSlots();

    verify(machineUpdate, never()).updateStatus(any(), any());
  }

  @Test
  void releaseExpiredSlots_multipleBookings_releasesOnlyEligibleMachines() {
    BookingStatus confirmedStatus =
        new BookingStatus(BookingParameters.BOOKING_STATUS_CONFIRMED, null);

    Machine machine2 = new Machine();
    machine2.setId(3);
    TimeSlot expiredSlot = new TimeSlot();
    expiredSlot.setId(1);
    expiredSlot.setStartTime(LocalTime.of(13, 0));
    TimeSlot activeSlot = new TimeSlot();
    activeSlot.setId(2);
    activeSlot.setStartTime(LocalTime.of(16, 0));

    Booking confirmedExpired = new Booking();
    confirmedExpired.setId("b-confirmed-expired");
    confirmedExpired.setDate(TODAY);
    confirmedExpired.setMachine(machine);
    confirmedExpired.setTimeSlot(expiredSlot);
    confirmedExpired.updateStatus(confirmedStatus);

    Booking confirmedActive = new Booking();
    confirmedActive.setId("b-confirmed-active");
    confirmedActive.setDate(TODAY);
    confirmedActive.setMachine(machine2);
    confirmedActive.setTimeSlot(activeSlot);
    confirmedActive.updateStatus(
        new BookingStatus(BookingParameters.BOOKING_STATUS_CONFIRMED, null));

    BookingEntity e1 = new BookingEntity();
    e1.setId("b-confirmed-expired");
    BookingEntity e2 = new BookingEntity();
    e2.setId("b-confirmed-active");

    when(bookingRepository.findByDate(TODAY))
        .thenReturn(Arrays.asList(confirmedExpired, confirmedActive));
    when(helper.hasNextBooking(confirmedExpired)).thenReturn(false);

    confirmService.releaseExpiredSlots();

    verify(machineUpdate).updateStatus(machine.getId(), BookingParameters.MACHINE_STATUS_AVAILABLE);
    verify(machineUpdate, never()).updateStatus(eq(machine2.getId()), any());
  }
}
