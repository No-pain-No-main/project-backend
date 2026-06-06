package com.adanext.NoPainNoMain.service.query;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;

@Service
public class BookingQuery {

    private final BookingRepositoryImpl bookingRepository;

    public BookingQuery(BookingRepositoryImpl bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking byId(String bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }
        
    public List<Booking> byMachine(Integer machineId) {
        return bookingRepository.findByMachineId(machineId);
    }

    public List<Booking> byDate(LocalDateTime date) {
        return bookingRepository.findByDate(date);
    }

    public List<Booking> byStudent(Student student) {
        return bookingRepository.findByStudent(student);
    }

    public boolean hasOverlappingBooking(Booking booking) {
        if (booking == null) {
            return false;
        }
        List<Booking> studentBookings = byStudent(booking.getStudent());

        return studentBookings.stream().anyMatch(b -> b.getDate().equals(booking.getDate()) 
                && b.getTimeSlot().getId().equals(booking.getTimeSlot().getId()));
                
    }
}
