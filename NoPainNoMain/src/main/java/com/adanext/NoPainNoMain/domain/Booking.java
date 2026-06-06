package com.adanext.NoPainNoMain.domain;

import java.time.LocalDate;

import com.adanext.NoPainNoMain.domain.types.BookingStatus;

public class Booking {

    private String id;
    private Student student;
    private Machine machine;
    private LocalDate date;
    private TimeSlot timeSlot;
    private BookingStatus bookingStatus; // Cambia a lo largo del ciclo de vida

    public Booking() {
        // Constructor vacío para frameworks que lo requieran
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

    // Getters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Student getStudent() { return student; }
    public Machine getMachine() { return machine; }
    public LocalDate getDate() { return date; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public BookingStatus getBookingStatus() { return bookingStatus; }

    // Método de negocio para controlar el cambio de estado de la reserva
    public void updateStatus(BookingStatus newStatus) {
        this.bookingStatus = newStatus;
    }
}