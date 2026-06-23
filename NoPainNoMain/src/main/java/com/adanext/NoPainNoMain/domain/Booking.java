package com.adanext.NoPainNoMain.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.adanext.NoPainNoMain.domain.types.BookingStatus;

public class Booking {

    private String id;
    private Student student;
    private Machine machine;
    private LocalDate date;
    private TimeSlot timeSlot;
    private BookingStatus bookingStatus; // Cambia a lo largo del ciclo de vida

    public Booking() {
        this.id = null;
        this.student = null;
        this.machine = null;
        this.date = null;
        this.timeSlot = null;
        this.bookingStatus = null;
    }

    public Booking(String id, Student student, Machine machine, LocalDate date,
                   TimeSlot timeSlot, BookingStatus bookingStatus) {
        this.id = id;
        this.student = student;
        this.machine = machine;
        this.date = date;
        this.timeSlot = timeSlot;
        this.bookingStatus = bookingStatus;
    }

    // ──────────────────────────────────────────────────
    // Comportamiento del dominio (lógica de negocio pura)
    // ──────────────────────────────────────────────────

    /**
     * Verifica si la fecha de la reserva es anterior al día actual.
     */
    public boolean isBeforeToday() {
        return date != null && date.isBefore(LocalDate.now());
    }

    /**
     * Verifica si la reserva cae en un día entre semana (lunes a viernes).
     */
    public boolean isWeekday() {
        if (date == null) return false;
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }

    /**
     * Verifica si la reserva está dentro de la semana actual (lunes a domingo).
     */
    public boolean isCurrentWeek() {
        if (date == null) return false;
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);
        return !date.isBefore(monday) && !date.isAfter(sunday);
    }

    /**
     * Verifica si la franja horaria de la reserva ya ha pasado en el día de hoy.
     */
    public boolean hasSlotPassed() {
        if (date == null || timeSlot == null) return false;

        LocalTime startTime = timeSlot.getStartTime();
        if (startTime == null) return false;

        LocalDateTime slotStart = LocalDateTime.of(date, startTime);
        return LocalDateTime.now().isAfter(slotStart);
    }

    /**
     * Verifica si ya es demasiado tarde para registrar la reserva.
     * Deben faltar al menos {@code minutesBefore} minutos para el inicio de la franja.
     */
    public boolean isTooLateToRegister(int minutesBefore) {
        if (date == null || timeSlot == null) return true;
        LocalTime startTime = timeSlot.getStartTime();
        if (startTime == null) return true;
        LocalDateTime slotStart = LocalDateTime.of(date, startTime);
        return LocalDateTime.now().isAfter(slotStart.minusMinutes(minutesBefore));
    }

    /**
     * Verifica si la reserva puede ser cancelada.
     * Deben faltar al menos {@code minutesBefore} minutos para el inicio de la franja.
     */
    public boolean canBeCancelled(int minutesBefore) {
        if (date == null || timeSlot == null) return false;
        LocalTime startTime = timeSlot.getStartTime();
        if (startTime == null) return false;

        LocalDateTime slotStart = LocalDateTime.of(date, startTime);
        return !LocalDateTime.now().isAfter(slotStart.minusMinutes(minutesBefore));
    }

    /**
     * Cancela la reserva cambiando su estado al estado cancelado.
     */
    public void cancel(BookingStatus cancelledStatus) {
        if (cancelledStatus == null) {
            throw new IllegalArgumentException("El estado 'Cancelada' no puede ser nulo");
        }
        this.bookingStatus = cancelledStatus;
    }

    /**
     * Verifica si la reserva está activa y corresponde al día indicado.
     */
    public boolean isActiveOnDate(LocalDate date) {
        return this.date != null && this.date.equals(date)
            && bookingStatus != null && bookingStatus.getId() == 1; // BOOKING_STATUS_ACTIVE
    }

    /**
     * Verifica si la reserva está lista para ser confirmada dentro de la ventana de confirmación.
     */
    public boolean isReadyForConfirmation(int windowMinutes) {
        if (timeSlot == null || timeSlot.getStartTime() == null) return false;
        LocalTime now = LocalTime.now();
        LocalTime slotStart = timeSlot.getStartTime();
        LocalTime windowStart = slotStart.minusMinutes(windowMinutes);
        return !now.isBefore(windowStart) && !now.isAfter(slotStart);
    }

    /**
     * Confirma la reserva cambiando su estado al estado confirmado.
     */
    public void confirm(BookingStatus confirmedStatus) {
        if (confirmedStatus == null) {
            throw new IllegalArgumentException("El estado 'Confirmada' no puede ser nulo");
        }
        this.bookingStatus = confirmedStatus;
    }

    /**
     * Actualiza el estado de la reserva (control de ciclo de vida).
     */
    public void updateStatus(BookingStatus newStatus) {
        this.bookingStatus = newStatus;
    }

    // Getters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Student getStudent() { return student; }
    public Machine getMachine() { return machine; }
    public LocalDate getDate() { return date; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public BookingStatus getBookingStatus() { return bookingStatus; }

    // Setters para deserialización Jackson
    public void setStudent(Student student) { this.student = student; }
    public void setMachine(Machine machine) { this.machine = machine; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTimeSlot(TimeSlot timeSlot) { this.timeSlot = timeSlot; }
}