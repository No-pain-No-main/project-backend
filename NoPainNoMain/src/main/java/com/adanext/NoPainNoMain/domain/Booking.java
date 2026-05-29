package com.adanext.NoPainNoMain.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.adanext.NoPainNoMain.domain.types.BookingStatus;

public class Booking {

    private  Integer id;
    private  Student student;
    private  Machine machine;
    private  LocalDateTime bookingDate;
    private  LocalDateTime startTime;
    private  LocalDateTime endTime;
    private BookingStatus bookingStatus; // Cambia a lo largo del ciclo de vida

    public Booking() {
        // Constructor vacío para frameworks que lo requieran
    }
    
    public Booking(Integer id, Student student, Machine machine, LocalDateTime bookingDate, 
                   LocalDateTime startTime, LocalDateTime endTime, BookingStatus bookingStatus) {
        this.id = id;
        this.student = student;
        this.machine = machine;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingStatus = bookingStatus;
    }

    // Campo calculado dinámicamente en minutos (ya seguro sin riesgo de NullPointerException)
    public Integer getDuration() {
        return (int) ChronoUnit.MINUTES.between(startTime, endTime);
    }

    // Getters
    public Integer getId() { return id; }
    public Student getStudent() { return student; }
    public Machine getMachine() { return machine; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public BookingStatus getBookingStatus() { return bookingStatus; }

    // Método de negocio para controlar el cambio de estado de la reserva
    public void updateStatus(BookingStatus newStatus) {
        this.bookingStatus = newStatus;
    }
}