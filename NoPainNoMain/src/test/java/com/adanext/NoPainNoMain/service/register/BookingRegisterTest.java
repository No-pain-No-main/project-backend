package com.adanext.NoPainNoMain.service.register;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.BookingRegisterHelper;
import com.adanext.NoPainNoMain.service.update.MachineUpdate;

@ExtendWith(MockitoExtension.class)
class BookingRegisterTest {

    @Mock
    private JsonToClass<Booking> jsonToClass;

    @Mock
    private BookingRepositoryImpl bookingRepository;

    @Mock
    private MachineUpdate machineUpdate;

    @Mock
    private BookingRegisterHelper helper;

    @InjectMocks
    private BookingRegister bookingRegister;

    private Booking validBooking;
    private Machine machine;
    private TimeSlot slot;
    private Student student;

    @BeforeEach
    void setUp() {
        machine = new Machine();
        machine.setId(1);

        slot = new TimeSlot();
        slot.setId(2);
        slot.setName("08:00-09:00");

        student = new Student();
        student.setDocumentNumber("11223344");

        LocalDate nextWeekday = nextWeekday();

        validBooking = new Booking();
        validBooking.setDate(nextWeekday);
        validBooking.setMachine(machine);
        validBooking.setTimeSlot(slot);
        validBooking.setStudent(student);
        validBooking.setId(nextWeekday + "_1_2");
    }

    private LocalDate nextWeekday() {
        LocalDate date = LocalDate.now();
        do {
            date = date.plusDays(1);
        } while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
        return date;
    }

    // ─── save(String jsonRegister) ────────────────────────────────────────────

    @Test
    void save_fromJson_generatesCompositeId() {
        when(jsonToClass.convert(any(), any())).thenReturn(validBooking);
        when(helper.isNull(any())).thenReturn(false);
        when(helper.isBeforeToday(any())).thenReturn(false);
        when(helper.isSlotAlreadyPassed(any())).thenReturn(false);
        when(helper.isWeekday(any())).thenReturn(true);
        when(helper.isCurrentWeek(any())).thenReturn(true);
        when(helper.existsById(any())).thenReturn(false);
        when(helper.hasReachedActiveLimit(any())).thenReturn(false);
        when(helper.isOverleap(any())).thenReturn(false);
        when(bookingRepository.save(any())).thenReturn(validBooking);

        Booking result = bookingRegister.save("{\"dummy\":\"json\"}");

        assertNotNull(result);
        verify(jsonToClass).convert("{\"dummy\":\"json\"}", Booking.class);
    }

    @Test
    void save_fromJson_idContainsDateMachineAndSlot() {
        Booking captured = new Booking();
        captured.setDate(validBooking.getDate());
        captured.setMachine(machine);
        captured.setTimeSlot(slot);
        captured.setStudent(student);

        when(jsonToClass.convert(any(), any())).thenReturn(captured);
        when(helper.isNull(any())).thenReturn(false);
        when(helper.isBeforeToday(any())).thenReturn(false);
        when(helper.isSlotAlreadyPassed(any())).thenReturn(false);
        when(helper.isWeekday(any())).thenReturn(true);
        when(helper.isCurrentWeek(any())).thenReturn(true);
        when(helper.existsById(any())).thenReturn(false);
        when(helper.hasReachedActiveLimit(any())).thenReturn(false);
        when(helper.isOverleap(any())).thenReturn(false);
        when(bookingRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Booking result = bookingRegister.save("{\"dummy\":\"json\"}");

        String expectedId = captured.getDate() + "_" + machine.getId() + "_" + slot.getId();
        assertEquals(expectedId, result.getId());
    }

    // ─── save(Booking booking) ────────────────────────────────────────────────

    @Test
    void save_validBooking_savesAndUpdatesMachineStatus() {
        when(helper.isNull(validBooking)).thenReturn(false);
        when(helper.isBeforeToday(validBooking)).thenReturn(false);
        when(helper.isSlotAlreadyPassed(validBooking)).thenReturn(false);
        when(helper.isWeekday(validBooking)).thenReturn(true);
        when(helper.isCurrentWeek(validBooking)).thenReturn(true);
        when(helper.existsById(validBooking)).thenReturn(false);
        when(helper.hasReachedActiveLimit(validBooking)).thenReturn(false);
        when(helper.isOverleap(validBooking)).thenReturn(false);
        when(bookingRepository.save(validBooking)).thenReturn(validBooking);

        Booking result = bookingRegister.save(validBooking);

        assertNotNull(result);
        verify(machineUpdate).updateStatus(machine.getId(), BookingParameters.MACHINE_STATUS_RESERVED);
        verify(bookingRepository).save(validBooking);
    }

    @Test
    void save_nullBooking_throwsIllegalArgumentException() {
        when(helper.isNull(null)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> bookingRegister.save((Booking) null));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void save_bookingWithPastDate_throwsIllegalStateException() {
        when(helper.isNull(validBooking)).thenReturn(false);
        when(helper.isBeforeToday(validBooking)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> bookingRegister.save(validBooking));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void save_bookingWithPassedSlot_throwsIllegalStateException() {
        when(helper.isNull(validBooking)).thenReturn(false);
        when(helper.isBeforeToday(validBooking)).thenReturn(false);
        when(helper.isSlotAlreadyPassed(validBooking)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> bookingRegister.save(validBooking));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void save_bookingOnWeekend_throwsIllegalStateException() {
        when(helper.isNull(validBooking)).thenReturn(false);
        when(helper.isBeforeToday(validBooking)).thenReturn(false);
        when(helper.isSlotAlreadyPassed(validBooking)).thenReturn(false);
        when(helper.isWeekday(validBooking)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> bookingRegister.save(validBooking));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void save_bookingOutsideCurrentWeek_throwsIllegalStateException() {
        when(helper.isNull(validBooking)).thenReturn(false);
        when(helper.isBeforeToday(validBooking)).thenReturn(false);
        when(helper.isSlotAlreadyPassed(validBooking)).thenReturn(false);
        when(helper.isWeekday(validBooking)).thenReturn(true);
        when(helper.isCurrentWeek(validBooking)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> bookingRegister.save(validBooking));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void save_duplicateBookingId_throwsIllegalStateException() {
        when(helper.isNull(validBooking)).thenReturn(false);
        when(helper.isBeforeToday(validBooking)).thenReturn(false);
        when(helper.isSlotAlreadyPassed(validBooking)).thenReturn(false);
        when(helper.isWeekday(validBooking)).thenReturn(true);
        when(helper.isCurrentWeek(validBooking)).thenReturn(true);
        when(helper.existsById(validBooking)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> bookingRegister.save(validBooking));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void save_studentAtActiveBookingLimit_throwsIllegalStateException() {
        when(helper.isNull(validBooking)).thenReturn(false);
        when(helper.isBeforeToday(validBooking)).thenReturn(false);
        when(helper.isSlotAlreadyPassed(validBooking)).thenReturn(false);
        when(helper.isWeekday(validBooking)).thenReturn(true);
        when(helper.isCurrentWeek(validBooking)).thenReturn(true);
        when(helper.existsById(validBooking)).thenReturn(false);
        when(helper.hasReachedActiveLimit(validBooking)).thenReturn(true);
        when(helper.studentDocumentNumber(validBooking)).thenReturn("11223344");
        when(bookingRepository.countActiveByStudent("11223344"))
                .thenReturn(BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT);

        assertThrows(IllegalStateException.class, () -> bookingRegister.save(validBooking));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void save_overlappingBooking_throwsIllegalStateException() {
        when(helper.isNull(validBooking)).thenReturn(false);
        when(helper.isBeforeToday(validBooking)).thenReturn(false);
        when(helper.isSlotAlreadyPassed(validBooking)).thenReturn(false);
        when(helper.isWeekday(validBooking)).thenReturn(true);
        when(helper.isCurrentWeek(validBooking)).thenReturn(true);
        when(helper.existsById(validBooking)).thenReturn(false);
        when(helper.hasReachedActiveLimit(validBooking)).thenReturn(false);
        when(helper.isOverleap(validBooking)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> bookingRegister.save(validBooking));
        verify(bookingRepository, never()).save(any());
    }
}
