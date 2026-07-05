package com.adanext.NoPainNoMain.service.query;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;

@Service
public class TodayBookingsQuery {

    private final BookingRepository bookingRepository;

    public TodayBookingsQuery(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> findTodayActiveBookings(String studentDocumentNumber) {
        LocalDate today = LocalDate.now();
        return bookingRepository.findByStudentDocumentNumber(studentDocumentNumber).stream()
            .filter(b -> b.isActiveOnDate(today))
            .collect(Collectors.toList());
    }
}