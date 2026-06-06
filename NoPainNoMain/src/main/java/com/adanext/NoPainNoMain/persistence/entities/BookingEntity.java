package com.adanext.NoPainNoMain.persistence.entities;

import java.time.LocalDateTime;

import com.adanext.NoPainNoMain.persistence.PersistenceConstants;
import com.adanext.NoPainNoMain.persistence.types.BookingStatusEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Booking", schema = PersistenceConstants.SCHEMA)
public class BookingEntity {


    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student; // Muchas reservas pertenecen a un Estudiante

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private MachineEntity machine; // Muchas reservas se hacen sobre una Máquina

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id", nullable = false)
    private TimeSlotEntity timeSlot; // Muchas reservas usan un TimeSlot

    @Column(name = "date", nullable = false)
    private LocalDateTime date; // Para búsquedas por fecha

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_status_id", nullable = false)
    private BookingStatusEntity bookingStatus; // Muchas reservas comparten un Estado

    // Requerido por JPA
    public BookingEntity() {}

    public BookingEntity( String id, StudentEntity student, MachineEntity machine, 
                         TimeSlotEntity timeSlot, LocalDateTime date, BookingStatusEntity bookingStatus) {
        this.id = id;
        this.student = student;
        this.machine = machine;
        this.timeSlot = timeSlot;
        this.date = date;
        this.bookingStatus = bookingStatus;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public StudentEntity getStudent() { return student; }
    public void setStudent(StudentEntity student) { this.student = student; }

    public MachineEntity getMachine() { return machine; }
    public void setMachine(MachineEntity machine) { this.machine = machine; }

    public TimeSlotEntity getTimeSlot() { return timeSlot; }
    public void setTimeSlot(TimeSlotEntity timeSlot) { this.timeSlot = timeSlot; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public BookingStatusEntity getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(BookingStatusEntity bookingStatus) { this.bookingStatus = bookingStatus; }

}
