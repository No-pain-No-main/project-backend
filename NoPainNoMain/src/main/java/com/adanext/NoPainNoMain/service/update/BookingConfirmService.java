package com.adanext.NoPainNoMain.service.update;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.persistence.entities.BookingEntity;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.repositories.BookingJpaRepository;
import com.adanext.NoPainNoMain.persistence.repositories.TimeSlotJpaRepository;

@Service
public class BookingConfirmService {

    private final BookingJpaRepository bookingJpaRepository;
    private final BookingRepositoryImpl bookingRepository;
    private final MachineUpdate machineUpdate;
    private final TimeSlotJpaRepository timeSlotJpaRepository;

    public BookingConfirmService(BookingJpaRepository bookingJpaRepository,
                                  BookingRepositoryImpl bookingRepository,
                                  MachineUpdate machineUpdate,
                                  TimeSlotJpaRepository timeSlotJpaRepository) {
        this.bookingJpaRepository = bookingJpaRepository;
        this.bookingRepository = bookingRepository;
        this.machineUpdate = machineUpdate;
        this.timeSlotJpaRepository = timeSlotJpaRepository;
    }

    public Booking confirm(String studentDocumentNumber) {
        List<Booking> todayActive = findTodayActiveBookings(studentDocumentNumber);

        if (todayActive.isEmpty()) {
            throw new IllegalStateException(
                "El estudiante " + studentDocumentNumber + " no tiene reservas activas para hoy"
            );
        }

        Booking bookingToConfirm = findBookingReadyToStart(todayActive);

        if (bookingToConfirm == null) {
            throw new IllegalStateException(
                "No hay ninguna reserva próxima a confirmar. Solo se pueden confirmar reservas dentro de los "
                + BookingParameters.CONFIRMATION_WINDOW_MINUTES
                + " minutos antes de su inicio"
            );
        }

        // Comportamiento del dominio: confirmar la reserva
        bookingToConfirm.confirm(
            new BookingStatus(BookingParameters.BOOKING_STATUS_CONFIRMED, null)
        );
        return bookingRepository.save(bookingToConfirm);
    }

    private List<Booking> findTodayActiveBookings(String studentDocumentNumber) {
        LocalDate today = LocalDate.now();
        return bookingJpaRepository
            .findByStudentDocumentNumber(studentDocumentNumber).stream()
            .map(entity -> bookingRepository.findById(entity.getId()).orElse(null))
            .filter(b -> b != null && b.isActiveOnDate(today))
            .collect(Collectors.toList());
    }

    private Booking findBookingReadyToStart(List<Booking> bookings) {
        for (Booking b : bookings) {
            if (b.isReadyForConfirmation(BookingParameters.CONFIRMATION_WINDOW_MINUTES)) {
                return b;
            }
        }
        return null;
    }

    private boolean hasNextBooking(Booking booking) {
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

    // ─── Tarea programada ─────────────────────────────────────────

    @Scheduled(cron = BookingParameters.RELEASE_CRON)
    public void releaseExpiredSlots() {
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        List<BookingEntity> allToday = bookingJpaRepository.findByDate(today);
        if (allToday.isEmpty()) return;

        for (var entity : allToday) {
            Booking booking = bookingRepository.findById(entity.getId()).orElse(null);
            if (booking == null) continue;

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
}