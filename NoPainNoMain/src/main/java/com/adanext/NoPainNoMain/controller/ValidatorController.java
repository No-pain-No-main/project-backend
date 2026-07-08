package com.adanext.NoPainNoMain.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.service.update.BookingConfirmService;

@RestController
@RequestMapping("/api/validator")
public class ValidatorController {

    private final BookingConfirmService bookingConfirmService;

    public ValidatorController(BookingConfirmService bookingConfirmService) {
        this.bookingConfirmService = bookingConfirmService;
    }

    
    @PostMapping("/confirm/{studentDocumentNumber}")
    public Object confirmBooking(@PathVariable String studentDocumentNumber) {
        try {
            Booking booking = bookingConfirmService.confirm(studentDocumentNumber);
            return booking;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}