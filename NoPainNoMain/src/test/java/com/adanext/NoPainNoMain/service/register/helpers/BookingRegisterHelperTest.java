package com.adanext.NoPainNoMain.service.register.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.TimeSlotRepositoryImpl;
import com.adanext.NoPainNoMain.service.query.BookingQuery;

@ExtendWith(MockitoExtension.class)
class BookingRegisterHelperTest {

    @Mock
    private BookingRepositoryImpl bookingRepository;

    @Mock
    private BookingQuery bookingQuery;

    @Mock
    private TimeSlotRepositoryImpl timeSlotRepository;

    @InjectMocks
    private BookingRegisterHelper helper;

    private Student student;
    private TimeSlot slot;
    private Booking booking;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setDocumentNumber("99887766");

        slot = new TimeSlot();
        slot.setId(2);

        booking = new Booking();
        booking.setDate(LocalDate.now().plusDays(1));
        booking.setStudent(student);
        booking.setTimeSlot(slot);
    }

    // ─── isSlotAlreadyPassed ──────────────────────────────────────────────────

    @Test
    void isSlotAlreadyPassed_whenSlotStartTimeIsInTheFuture_returnsFalse() {
        LocalDateTime futureDateTime = LocalDateTime.now().plusHours(2);
        
        slot.setStartTime(futureDateTime.toLocalTime());
        booking.setDate(futureDateTime.toLocalDate());
        
        boolean result = helper.isSlotAlreadyPassed(booking);

        assertFalse(result);
    }

    @Test
    void isSlotAlreadyPassed_whenSlotStartTimeIsInThePast_returnsTrue() {
        LocalDateTime pastDateTime = LocalDateTime.now().minusHours(3);
        
        slot.setStartTime(pastDateTime.toLocalTime());
        booking.setDate(pastDateTime.toLocalDate());

        boolean result = helper.isSlotAlreadyPassed(booking);

        assertTrue(result);
    }

    @Test
    void isSlotAlreadyPassed_whenBookingIsForFutureDate_returnsFalse() {
        slot.setStartTime(LocalTime.of(7, 0));
        booking.setDate(LocalDate.now().plusDays(1));

        boolean result = helper.isSlotAlreadyPassed(booking);

        assertFalse(result);
    }

    @Test
    void isSlotAlreadyPassed_whenSlotHasNoStartTimeButHasId_resolvesFromRepositoryAndChecks() {
        TimeSlot slotWithoutTime = new TimeSlot();
        slotWithoutTime.setId(5);

        LocalDateTime pastDateTime = LocalDateTime.now().minusHours(3);

        TimeSlot resolvedSlot = new TimeSlot();
        resolvedSlot.setId(5);
        resolvedSlot.setStartTime(pastDateTime.toLocalTime()); 

        booking.setTimeSlot(slotWithoutTime);
        booking.setDate(pastDateTime.toLocalDate());

        when(timeSlotRepository.findById(5)).thenReturn(Optional.of(resolvedSlot));

        boolean result = helper.isSlotAlreadyPassed(booking);

        assertTrue(result);
    }

    @Test
    void isSlotAlreadyPassed_whenBookingDateIsNull_returnsFalse() {
        booking.setDate(null);

        boolean result = helper.isSlotAlreadyPassed(booking);

        assertFalse(result);
    }

    @Test
    void isSlotAlreadyPassed_whenTimeSlotIsNull_returnsFalse() {
        booking.setTimeSlot(null);

        boolean result = helper.isSlotAlreadyPassed(booking);

        assertFalse(result);
    }

    // ─── hasReachedActiveLimit ────────────────────────────────────────────────

    @Test
    void hasReachedActiveLimit_whenStudentBelowLimit_returnsFalse() {
        when(bookingRepository.countActiveByStudent("99887766"))
                .thenReturn(BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT - 1);

        boolean result = helper.hasReachedActiveLimit(booking);

        assertFalse(result);
    }

    @Test
    void hasReachedActiveLimit_whenStudentAtExactLimit_returnsTrue() {
        when(bookingRepository.countActiveByStudent("99887766"))
                .thenReturn(BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT);

        boolean result = helper.hasReachedActiveLimit(booking);

        assertTrue(result);
    }

    @Test
    void hasReachedActiveLimit_whenStudentExceedsLimit_returnsTrue() {
        when(bookingRepository.countActiveByStudent("99887766"))
                .thenReturn(BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT + 2);

        boolean result = helper.hasReachedActiveLimit(booking);

        assertTrue(result);
    }

    @Test
    void hasReachedActiveLimit_whenStudentIsNull_returnsFalse() {
        booking.setStudent(null);

        boolean result = helper.hasReachedActiveLimit(booking);

        assertFalse(result);
    }

    @Test
    void hasReachedActiveLimit_whenStudentDocumentNumberIsNull_returnsFalse() {
        student.setDocumentNumber(null);
        booking.setStudent(student);

        boolean result = helper.hasReachedActiveLimit(booking);

        assertFalse(result);
    }
}
