package com.adanext.NoPainNoMain.persistence.entities;

import java.time.LocalTime;
import java.util.List;

import com.adanext.NoPainNoMain.persistence.PersistenceConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "time_slot", schema = PersistenceConstants.SCHEMA)
public class TimeSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @OneToMany(mappedBy = "timeSlot")
    private List<BookingEntity> bookings;

    // Requerido por JPA
    public TimeSlotEntity() {}

    public TimeSlotEntity(Integer id, String name, LocalTime startTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime;}

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }
}
