package com.adanext.NoPainNoMain.domain;

import java.time.LocalDate;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;

public class Booking {

    private String id; 
    private Student student;
    private Machine machine;
    private LocalDate date;
    private TimeSlot timeSlot;
    private BookingStatus bookingStatus; 

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

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public Machine getMachine() { return machine; }
    public void setMachine(Machine machine) { this.machine = machine; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public TimeSlot getTimeSlot() { return timeSlot; }
    public void setTimeSlot(TimeSlot timeSlot) { this.timeSlot = timeSlot; }
    
    public BookingStatus getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }
    
    // Método de negocio para controlar el cambio de estado de la reserva
    public void updateStatus(BookingStatus newStatus) {
        this.bookingStatus = newStatus;
    }
}