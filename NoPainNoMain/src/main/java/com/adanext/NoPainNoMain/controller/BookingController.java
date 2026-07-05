package com.adanext.NoPainNoMain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.service.query.BookingQuery;
import com.adanext.NoPainNoMain.service.register.BookingRegister;
import com.adanext.NoPainNoMain.service.update.BookingCancelService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingQuery bookingQuery;
    private final BookingRegister bookingRegister;
    private final BookingCancelService bookingCancelService;

    public BookingController(BookingQuery bookingQuery,
                              BookingRegister bookingRegister,
                              BookingCancelService bookingCancelService) {
        this.bookingQuery = bookingQuery;
        this.bookingRegister = bookingRegister;
        this.bookingCancelService = bookingCancelService;
    }

    @GetMapping("/{bookingId}")
    public Object getById(@PathVariable String bookingId) {
        Booking booking = bookingQuery.byId(bookingId);
        if (booking == null) {
            return "Reserva con ID " + bookingId + " no encontrada";
        }
        return booking;
    }

    @PostMapping
    public Object register(@RequestBody String json) {
        try {
            Booking booking = bookingRegister.save(json);
            return booking;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/{bookingId}/cancel")
    public Object cancel(@PathVariable String bookingId) {
        try {
            Booking booking = bookingCancelService.cancel(bookingId);
            return booking;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}