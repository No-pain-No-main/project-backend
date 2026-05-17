package com.adanext.NoPainNoMain.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.adanext.NoPainNoMain.domain.types.BookingStatus;

public class Booking {

    private Integer id;
    private Student student;
    private Machine machine;
    private LocalDateTime bookingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus bookingStatus;

    public Booking() {}

    // Campo calculado dinámicamente (por ejemplo, en minutos)
    public Integer getDuration() {
        if (startTime != null && endTime != null) {
            return (int) ChronoUnit.MINUTES.between(startTime, endTime);
        }
        return 0;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Machine getMachine() { return machine; }
    public void setMachine(Machine machine) { this.machine = machine; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public BookingStatus getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }
}