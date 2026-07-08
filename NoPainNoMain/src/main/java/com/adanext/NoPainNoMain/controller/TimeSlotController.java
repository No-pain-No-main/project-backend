package com.adanext.NoPainNoMain.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.domain.repository.TimeSlotRepository;

@RestController
@RequestMapping("/api/time-slots")
public class TimeSlotController {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotController(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    @GetMapping
    public List<TimeSlot> getAll() {
        return timeSlotRepository.findAll();
    }
}
