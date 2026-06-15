package com.adanext.NoPainNoMain.service.register;

import com.adanext.NoPainNoMain.config.BookingParameters;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.service.jsonconverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.BookingRegisterHelper;
import com.adanext.NoPainNoMain.service.update.MachineUpdate;
import org.springframework.stereotype.Service;

@Service
public class BookingRegister {

  private final JsonToClass<Booking> jsonToClass;
  private final BookingRepository bookingRepository;
  private final MachineUpdate machineUpdate;
  private final BookingRegisterHelper helper;

  public BookingRegister(
      JsonToClass<Booking> jsonToClass,
      BookingRepository bookingRepository,
      MachineUpdate machineUpdate,
      BookingRegisterHelper helper) {
    this.jsonToClass = jsonToClass;
    this.bookingRepository = bookingRepository;
    this.machineUpdate = machineUpdate;
    this.helper = helper;
  }

  public Booking save(String jsonRegister) {
    Booking booking = jsonToClass.convert(jsonRegister, Booking.class);
    // Auto-generar id: date_machineId_slotId
    booking.setId(
        booking.getDate()
            + "_"
            + booking.getMachine().getId()
            + "_"
            + booking.getTimeSlot().getId());
    return save(booking);
  }

  public Booking save(Booking booking) {
    if (helper.isNull(booking)) {
      throw new IllegalArgumentException("La reserva no puede ser nula");
    }

    if (helper.isBeforeToday(booking)) {
      throw new IllegalStateException(
          "No se puede reservar para una fecha anterior al día de hoy ("
              + java.time.LocalDate.now()
              + ")");
    }

    if (helper.isSlotAlreadyPassed(booking)) {
      throw new IllegalStateException(
          "La franja "
              + booking.getTimeSlot().getName()
              + " ya pasó. No se puede reservar para un horario anterior al actual");
    }

    if (!helper.isWeekday(booking)) {
      throw new IllegalStateException(
          "Solo se pueden hacer reservas entre semana (lunes a viernes). El "
              + booking.getDate().getDayOfWeek()
              + " no está permitido");
    }

    if (!helper.isCurrentWeek(booking)) {
      throw new IllegalStateException(
          "Solo se pueden hacer reservas dentro de la semana actual. La fecha "
              + booking.getDate()
              + " está fuera de la semana vigente");
    }

    if (helper.existsById(booking)) {
      throw new IllegalStateException(
          "La reserva con ID " + booking.getId() + " ya existe en el sistema");
    }

    if (helper.hasReachedActiveLimit(booking)) {
      int active = bookingRepository.countActiveByStudent(helper.studentDocumentNumber(booking));
      throw new IllegalStateException(
          "El estudiante "
              + helper.studentDocumentNumber(booking)
              + " ya tiene "
              + active
              + " reservas activas (máximo: "
              + BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT
              + ")");
    }

    if (helper.isOverleap(booking)) {
      throw new IllegalStateException(
          "El estudiante ya tiene una reserva solapada o la máquina está ocupada en ese intervalo");
    }

    if (booking == null) {
      throw new IllegalArgumentException("Datos de reserva inválidos");
    }

    // Cambiar estado de la máquina a "Reservada"
    machineUpdate.updateStatus(
        booking.getMachine().getId(), BookingParameters.MACHINE_STATUS_RESERVED);

    return bookingRepository.save(booking);
  }
}
