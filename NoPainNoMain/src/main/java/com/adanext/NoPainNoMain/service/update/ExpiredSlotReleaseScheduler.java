package com.adanext.NoPainNoMain.service.update;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;

@Service
public class ExpiredSlotReleaseScheduler {

    private final BookingRepository bookingRepository;
    private final MachineUpdate machineUpdate;

    public ExpiredSlotReleaseScheduler(BookingRepository bookingRepository,
                                        MachineUpdate machineUpdate) {
        this.bookingRepository = bookingRepository;
        this.machineUpdate = machineUpdate;
    }

    @Scheduled(cron = BookingParameters.RELEASE_CRON)
    public void releaseExpiredSlots() {
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        List<Booking> allToday = bookingRepository.findByDate(today);
        if (allToday.isEmpty()) return;

        for (Booking booking : allToday) {
            boolean isConfirmed = booking.getBookingStatus() != null
                && booking.getBookingStatus().getId() == BookingParameters.BOOKING_STATUS_CONFIRMED;
            boolean slotEnded = booking.getTimeSlot() != null
                && booking.getTimeSlot().getStartTime() != null
                && currentTime.isAfter(booking.getTimeSlot().getStartTime().plusHours(1));

            if (isConfirmed && slotEnded && !hasNextBooking(booking)) {
                machineUpdate.updateStatus(
                    booking.getMachine().getId(),
                    BookingParameters.MACHINE_STATUS_AVAILABLE
                );
            }
        }
    }

    private boolean hasNextBooking(Booking booking) {
        if (booking.getTimeSlot() == null) return false;
        int nextSlotId = booking.getTimeSlot().getId() + 1;

        Booking nextBooking = bookingRepository.findById(
            booking.getDate() + "_" + booking.getMachine().getId() + "_" + nextSlotId
        ).orElse(null);

        return nextBooking != null;
    }
}