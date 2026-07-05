package com.adanext.NoPainNoMain.service.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.TimeSlotRepositoryImpl;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

  @Mock private BookingRepositoryImpl bookingRepository;

  @Mock private MachineRepositoryImpl machineRepository;

  @Mock private TimeSlotRepositoryImpl timeSlotRepository;

  @InjectMocks private AvailabilityService availabilityService;

  private TimeSlot slot1, slot2, slot3;
  private LocalDate testDate;
  private Integer machineId;

  @BeforeEach
  void setUp() {
    testDate = LocalDate.now().plusDays(1);
    machineId = 1;

    slot1 = new TimeSlot();
    slot1.setId(1);

    slot2 = new TimeSlot();
    slot2.setId(2);

    slot3 = new TimeSlot();
    slot3.setId(3);
  }

  @Test
  void findFreeSlotsByMachine_whenNoBookingsExist_returnsAllSlots() {
    when(timeSlotRepository.findAll()).thenReturn(Arrays.asList(slot1, slot2, slot3));
    when(bookingRepository.findByMachineIdAndDateBetween(eq(machineId), eq(testDate), eq(testDate)))
        .thenReturn(Collections.emptyList());

    List<TimeSlot> freeSlots = availabilityService.findFreeSlotsByMachine(machineId, testDate);

    assertEquals(3, freeSlots.size());
    assertTrue(freeSlots.containsAll(Arrays.asList(slot1, slot2, slot3)));
  }

  @Test
  void findFreeSlotsByMachine_whenSomeSlotsBooked_returnsOnlyFreeSlots() {
    Booking booking = new Booking();
    booking.setTimeSlot(slot1);

    when(timeSlotRepository.findAll()).thenReturn(Arrays.asList(slot1, slot2, slot3));
    when(bookingRepository.findByMachineIdAndDateBetween(eq(machineId), eq(testDate), eq(testDate)))
        .thenReturn(Collections.singletonList(booking));

    List<TimeSlot> freeSlots = availabilityService.findFreeSlotsByMachine(machineId, testDate);

    assertEquals(2, freeSlots.size());
    assertTrue(freeSlots.contains(slot2));
    assertTrue(freeSlots.contains(slot3));
    assertTrue(freeSlots.stream().noneMatch(s -> s.getId().equals(1)));
  }

  @Test
  void findFreeSlotsByMachine_whenAllSlotsBooked_returnsEmptyList() {
    Booking b1 = new Booking();
    b1.setTimeSlot(slot1);
    Booking b2 = new Booking();
    b2.setTimeSlot(slot2);
    Booking b3 = new Booking();
    b3.setTimeSlot(slot3);

    when(timeSlotRepository.findAll()).thenReturn(Arrays.asList(slot1, slot2, slot3));
    when(bookingRepository.findByMachineIdAndDateBetween(eq(machineId), eq(testDate), eq(testDate)))
        .thenReturn(Arrays.asList(b1, b2, b3));

    List<TimeSlot> freeSlots = availabilityService.findFreeSlotsByMachine(machineId, testDate);

    assertTrue(freeSlots.isEmpty());
  }

  @Test
  void findFreeSlotsByMachine_doesNotReturnSlotsBookedByOtherMachines() {
    Booking bookingForOtherMachine = new Booking();
    bookingForOtherMachine.setTimeSlot(slot2);

    when(timeSlotRepository.findAll()).thenReturn(Arrays.asList(slot1, slot2, slot3));
    when(bookingRepository.findByMachineIdAndDateBetween(eq(machineId), eq(testDate), eq(testDate)))
        .thenReturn(Collections.emptyList());

    List<TimeSlot> freeSlots = availabilityService.findFreeSlotsByMachine(machineId, testDate);

    assertEquals(3, freeSlots.size());
    assertTrue(freeSlots.containsAll(Arrays.asList(slot1, slot2, slot3)));
  }

  @Test
  void
      findFreeSlotsByMachine_whenMultipleBookingsForSameSlot_returnsThatSlotOnlyOnceAsUnavailable() {
    Booking b1 = new Booking();
    b1.setTimeSlot(slot1);
    Booking b2 = new Booking();
    b2.setTimeSlot(slot1);

    when(timeSlotRepository.findAll()).thenReturn(Arrays.asList(slot1, slot2, slot3));
    when(bookingRepository.findByMachineIdAndDateBetween(eq(machineId), eq(testDate), eq(testDate)))
        .thenReturn(Arrays.asList(b1, b2));

    List<TimeSlot> freeSlots = availabilityService.findFreeSlotsByMachine(machineId, testDate);

    assertEquals(2, freeSlots.size());
    assertTrue(freeSlots.stream().noneMatch(s -> s.getId().equals(1)));
  }
}
