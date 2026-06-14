package com.adanext.NoPainNoMain.service.update.helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.repositories.BookingJpaRepository;
import com.adanext.NoPainNoMain.persistence.repositories.TimeSlotJpaRepository;

@Component
public class BookingConfirmHelper {

    private final BookingJpaRepository bookingJpaRepository;
    private final BookingRepositoryImpl bookingRepository;
    private final TimeSlotJpaRepository timeSlotJpaRepository;

    public BookingConfirmHelper(BookingJpaRepository bookingJpaRepository,
                                 BookingRepositoryImpl bookingRepository,
                                 TimeSlotJpaRepository timeSlotJpaRepository) {
        this.bookingJpaRepository = bookingJpaRepository;
        this.bookingRepository = bookingRepository;
        this.timeSlotJpaRepository = timeSlotJpaRepository;
    }

    public List<Booking> findTodayActiveBookings(String studentDocumentNumber) {
        LocalDate today = LocalDate.now();
        return bookingJpaRepository
            .findByStudentDocumentNumber(studentDocumentNumber).stream()
            .map(entity -> bookingRepository.findById(entity.getId()).orElse(null))
            .filter(b -> isActiveToday(b, today))
            .collect(Collectors.toList());
    }

    public boolean isActiveToday(Booking booking, LocalDate today) {
        return booking != null
            && booking.getDate() != null && booking.getDate().equals(today)
            && booking.getBookingStatus() != null
            && booking.getBookingStatus().getId() == BookingParameters.BOOKING_STATUS_ACTIVE;
    }

    public Booking findBookingReadyToStart(List<Booking> bookings) {
        LocalTime currentTime = LocalTime.now();
        for (Booking b : bookings) {
            if (b.getTimeSlot() == null || b.getTimeSlot().getStartTime() == null) continue;
            LocalTime slotStart = b.getTimeSlot().getStartTime();
            LocalTime windowStart = slotStart.minusMinutes(BookingParameters.CONFIRMATION_WINDOW_MINUTES);
            if (!currentTime.isBefore(windowStart) && !currentTime.isAfter(slotStart)) {
                return b;
            }
        }
        return null;
    }

    public void confirmBooking(Booking booking) {
        booking.updateStatus(
            new BookingStatus(BookingParameters.BOOKING_STATUS_CONFIRMED, null)
        );
    }

    public boolean hasNextBooking(Booking booking) {
        if (booking.getTimeSlot() == null) return false;
        int nextSlotId = booking.getTimeSlot().getId() + 1;
        return timeSlotJpaRepository.findById(nextSlotId)
            .map(nextSlot -> bookingJpaRepository
                .findByMachineIdAndDateBetween(
                    booking.getMachine().getId(),
                    booking.getDate(), booking.getDate()
                ).stream().anyMatch(b ->
                    b.getTimeSlot() != null && b.getTimeSlot().getId().equals(nextSlotId)
                ))
            .orElse(false);
    }
}