package com.adanext.NoPainNoMain.service.update;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.service.update.helpers.BookingCancelHelper;

@ExtendWith(MockitoExtension.class)
class BookingCancelServiceTest {

    @Mock
    private BookingRepositoryImpl bookingRepository;

    @Mock
    private BookingCancelHelper helper;

    @Mock
    private MachineUpdate machineUpdate;

    @InjectMocks
    private BookingCancelService cancelService;

    private Booking booking;
    private Machine machine;
    private TimeSlot slot;

    @BeforeEach
    void setUp() {
        LocalDateTime baseTime = LocalDateTime.now();

        machine = new Machine();
        machine.setId(5);

        LocalDateTime slotDateTime = baseTime.plusHours(2);

        slot = new TimeSlot();
        slot.setId(2);
        slot.setName("09:00-10:00");
        slot.setStartTime(slotDateTime.toLocalTime());

        booking = new Booking();
        booking.setId("2024-06-10_5_2");
        booking.setDate(slotDateTime.toLocalDate());
        booking.setMachine(machine);
        booking.setTimeSlot(slot);
    }

    @Test
    void cancel_existingCancellableBooking_cancelsAndFreeMachine() {
        when(bookingRepository.findById("2024-06-10_5_2")).thenReturn(Optional.of(booking));
        when(helper.canBeCancelled(booking)).thenReturn(true);
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = cancelService.cancel("2024-06-10_5_2");

        assertNotNull(result);
        verify(machineUpdate).updateStatus(machine.getId(), BookingParameters.MACHINE_STATUS_AVAILABLE);
        verify(helper).cancelBooking(booking);
        verify(bookingRepository).save(booking);
    }

    @Test
    void cancel_nonExistentBooking_throwsIllegalStateException() {
        when(bookingRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> cancelService.cancel("nonexistent"));
        verify(machineUpdate, never()).updateStatus(any(), any());
        verify(helper, never()).cancelBooking(any());
    }

    @Test
    void cancel_bookingTooCloseToSlotStart_throwsIllegalStateException() {
        when(bookingRepository.findById("2024-06-10_5_2")).thenReturn(Optional.of(booking));
        when(helper.canBeCancelled(booking)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> cancelService.cancel("2024-06-10_5_2"));
        verify(machineUpdate, never()).updateStatus(any(), any());
        verify(helper, never()).cancelBooking(any());
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void cancel_bookingTooCloseToStart_errorMessageContainsCancellationMinutes() {
        when(bookingRepository.findById("2024-06-10_5_2")).thenReturn(Optional.of(booking));
        when(helper.canBeCancelled(booking)).thenReturn(false);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> cancelService.cancel("2024-06-10_5_2"));

        assertTrue(ex.getMessage().contains(String.valueOf(BookingParameters.CANCELLATION_MINUTES_BEFORE)));
    }

    @Test
    void cancel_machineIsReleasedBeforeCancellingBooking() {
        when(bookingRepository.findById("2024-06-10_5_2")).thenReturn(Optional.of(booking));
        when(helper.canBeCancelled(booking)).thenReturn(true);
        when(bookingRepository.save(booking)).thenReturn(booking);

        cancelService.cancel("2024-06-10_5_2");

        org.mockito.InOrder inOrder = org.mockito.Mockito.inOrder(machineUpdate, helper, bookingRepository);
        inOrder.verify(machineUpdate).updateStatus(machine.getId(), BookingParameters.MACHINE_STATUS_AVAILABLE);
        inOrder.verify(helper).cancelBooking(booking);
        inOrder.verify(bookingRepository).save(booking);
    }

    @Test
    void cancel_returnsUpdatedBookingFromRepository() {
        Booking savedBooking = new Booking();
        savedBooking.setId("2024-06-10_5_2");

        when(bookingRepository.findById("2024-06-10_5_2")).thenReturn(Optional.of(booking));
        when(helper.canBeCancelled(booking)).thenReturn(true);
        when(bookingRepository.save(booking)).thenReturn(savedBooking);

        Booking result = cancelService.cancel("2024-06-10_5_2");

        assertEquals("2024-06-10_5_2", result.getId());
    }

}
