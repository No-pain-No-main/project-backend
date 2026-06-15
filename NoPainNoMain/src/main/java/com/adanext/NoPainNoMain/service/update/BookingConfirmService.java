package com.adanext.NoPainNoMain.service.update;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.service.update.helpers.BookingConfirmHelper;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BookingConfirmService {

  private final BookingRepository bookingRepository;
  private final MachineUpdate machineUpdate;
  private final BookingConfirmHelper helper;
  private Clock clock = Clock.systemDefaultZone();

  public void setClock(Clock clock) {
    this.clock = clock;
  }

  public BookingConfirmService(
      BookingRepository bookingRepository,
      MachineUpdate machineUpdate,
      BookingConfirmHelper helper) {
    this.bookingRepository = bookingRepository;
    this.machineUpdate = machineUpdate;
    this.helper = helper;
  }

  public Booking confirm(String studentDocumentNumber) {
    List<Booking> todayActive = helper.findTodayActiveBookings(studentDocumentNumber);

    if (todayActive.isEmpty()) {
      throw new IllegalStateException(
          "El estudiante " + studentDocumentNumber + " no tiene reservas activas para hoy");
    }

    Booking bookingToConfirm = helper.findBookingReadyToStart(todayActive);

    if (bookingToConfirm == null) {
      throw new IllegalStateException(
          "No hay ninguna reserva próxima a confirmar."
              + " Solo se pueden confirmar reservas dentro de los "
              + BookingParameters.CONFIRMATION_WINDOW_MINUTES
              + " minutos antes de su inicio");
    }

    helper.confirmBooking(bookingToConfirm);
    return bookingRepository.save(bookingToConfirm);
  }

  // ─── Tarea programada ─────────────────────────────────────────

  @Scheduled(cron = BookingParameters.RELEASE_CRON)
  public void releaseExpiredSlots() {
    LocalDate today = LocalDate.now(clock);
    LocalTime currentTime = LocalTime.now(clock);

    List<Booking> allToday = bookingRepository.findByDate(today);
    if (allToday.isEmpty()) {
      return;
    }

    for (Booking booking : allToday) {
      boolean isConfirmed =
          booking.getBookingStatus() != null
              && booking.getBookingStatus().getId() == BookingParameters.BOOKING_STATUS_CONFIRMED;
      boolean slotEnded =
          booking.getTimeSlot() != null
              && booking.getTimeSlot().getStartTime() != null
              && currentTime.isAfter(booking.getTimeSlot().getStartTime().plusHours(1));

      if (isConfirmed && slotEnded && !helper.hasNextBooking(booking)) {
        machineUpdate.updateStatus(
            booking.getMachine().getId(), BookingParameters.MACHINE_STATUS_AVAILABLE);
      }
    }
  }
}
