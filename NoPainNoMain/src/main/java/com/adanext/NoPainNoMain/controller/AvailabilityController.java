package com.adanext.NoPainNoMain.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.service.query.AvailabilityService;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    /**
     * Devuelve los horarios libres de una máquina específica en una fecha.
     */
    @GetMapping("/{machineId}/{date}")
    public Object getByMachine(@PathVariable Integer machineId, @PathVariable String date) {
        LocalDate day = LocalDate.parse(date);
        List<TimeSlot> freeSlots = availabilityService.findFreeSlotsByMachine(machineId, day);
        return freeSlots.stream().map(TimeSlot::getName).collect(Collectors.toList());
    }

    /**
     * Devuelve los horarios libres de todas las máquinas en una fecha.
     */
    @GetMapping("/{date}")
    public Object getAll(@PathVariable String date) {
        LocalDate day = LocalDate.parse(date);
        var allAvailability = availabilityService.findFreeSlotsForAllMachines(day);
        return allAvailability.stream().collect(Collectors.toMap(
            a -> a.getMachine().getName(),
            a -> a.getFreeSlots().stream().map(TimeSlot::getName).collect(Collectors.toList())
        ));
    }
}