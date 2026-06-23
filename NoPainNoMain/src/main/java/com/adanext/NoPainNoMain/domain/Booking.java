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
    private BookingStatus bookingStatus; 
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

   
    public boolean isBeforeToday() {
        return date != null && date.isBefore(LocalDate.now());
    }

   
    public boolean isWeekday() {
        if (date == null) return false;
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }

   
    public boolean isCurrentWeek() {
        if (date == null) return false;
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);
        return !date.isBefore(monday) && !date.isAfter(sunday);
    }

    
    public boolean hasSlotPassed() {
        if (date == null || timeSlot == null) return false;

        LocalTime startTime = timeSlot.getStartTime();
        if (startTime == null) return false;

        LocalDateTime slotStart = LocalDateTime.of(date, startTime);
        return LocalDateTime.now().isAfter(slotStart);
    }

    
    public boolean isTooLateToRegister(int minutesBefore) {
        if (date == null || timeSlot == null) return true;
        LocalTime startTime = timeSlot.getStartTime();
        if (startTime == null) return true;
        LocalDateTime slotStart = LocalDateTime.of(date, startTime);
        return LocalDateTime.now().isAfter(slotStart.minusMinutes(minutesBefore));
    }

    
    public boolean canBeCancelled(int minutesBefore) {
        if (date == null || timeSlot == null) return false;
        LocalTime startTime = timeSlot.getStartTime();
        if (startTime == null) return false;

        LocalDateTime slotStart = LocalDateTime.of(date, startTime);
        return !LocalDateTime.now().isAfter(slotStart.minusMinutes(minutesBefore));
    }

   
    public void cancel(BookingStatus cancelledStatus) {
        if (cancelledStatus == null) {
            throw new IllegalArgumentException("El estado 'Cancelada' no puede ser nulo");
        }
        this.bookingStatus = cancelledStatus;
    }

   
    public boolean isActiveOnDate(LocalDate date) {
        return this.date != null && this.date.equals(date)
            && bookingStatus != null && bookingStatus.getId() == 1; // BOOKING_STATUS_ACTIVE
    }

   
    public boolean isReadyForConfirmation(int windowMinutes) {
        if (timeSlot == null || timeSlot.getStartTime() == null) return false;
        LocalTime now = LocalTime.now();
        LocalTime slotStart = timeSlot.getStartTime();
        LocalTime windowStart = slotStart.minusMinutes(windowMinutes);
        return !now.isBefore(windowStart) && !now.isAfter(slotStart);
    }

    
    public void confirm(BookingStatus confirmedStatus) {
        if (confirmedStatus == null) {
            throw new IllegalArgumentException("El estado 'Confirmada' no puede ser nulo");
        }
        this.bookingStatus = confirmedStatus;
    }

    
    public void updateStatus(BookingStatus newStatus) {
        this.bookingStatus = newStatus;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Student getStudent() { return student; }
    public Machine getMachine() { return machine; }
    public LocalDate getDate() { return date; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public BookingStatus getBookingStatus() { return bookingStatus; }

    public void setStudent(Student student) { this.student = student; }
    public void setMachine(Machine machine) { this.machine = machine; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTimeSlot(TimeSlot timeSlot) { this.timeSlot = timeSlot; }
}