package com.adanext.NoPainNoMain.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.ClassToJson;
import com.adanext.NoPainNoMain.service.query.AvailabilityService;
import com.adanext.NoPainNoMain.service.query.MachineQuery;
import com.adanext.NoPainNoMain.service.query.StudentQuery;
import com.adanext.NoPainNoMain.service.register.AdministratorRegister;
import com.adanext.NoPainNoMain.service.register.BookingRegister;
import com.adanext.NoPainNoMain.service.register.MachineRegister;
import com.adanext.NoPainNoMain.service.register.StudentRegister;


@RestController
@RequestMapping("/api/test")
public class TestJsonController {

    private final ClassToJson classToJson;
    private final StudentQuery studentQuery;
    private final StudentRegister studentRegister;
    private final BookingRegister bookingRegister;
    private final MachineRegister machineRegister;
    private final MachineQuery machineQuery;
    private final AdministratorRegister administratorRegister;
    private final BookingRepositoryImpl bookingRepository;
    private final AvailabilityService availabilityService;

    public TestJsonController(ClassToJson classToJson, StudentQuery studentQuery,StudentRegister studentRegister, BookingRegister bookingRegister, MachineRegister machineRegister, MachineQuery machineQuery, AdministratorRegister administratorRegister, BookingRepositoryImpl bookingRepository, AvailabilityService availabilityService){
        this.classToJson = classToJson;
        this.studentQuery = studentQuery;
        this.studentRegister = studentRegister;
        this.bookingRegister = bookingRegister;
        this.machineRegister = machineRegister;
        this.machineQuery = machineQuery;
        this.administratorRegister = administratorRegister;
        this.bookingRepository = bookingRepository;
        this.availabilityService = availabilityService;
    }

    @PostMapping("/booking")
    Object registerBooking(@RequestBody String json){
        try {
            Booking booking = bookingRegister.save(json);
            System.out.println("Registered booking: " + booking.getId());
            return booking;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/machine")
    Object registerMachine(@RequestBody String json){
        try {
            Machine machine = machineRegister.save(json);
            System.out.println("Registered machine: " + machine.getId());
            return machine;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/student")
    Object registerStudent(@RequestBody String json){
        try {
            Student student = studentRegister.save(json);
            System.out.println("Registered student: " + student.getFirstName());
            return student;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }


    @PostMapping("/administrator")
    Object registerAdministrator(@RequestBody String json){
        try {
            Administrator saved = administratorRegister.save(json);
            System.out.println("Registered administrator: " + saved.getFirstName());
            return saved;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/2")
    String getTestJson() {
        Student student = studentQuery.studentByDocumentNumber("12345678");
        String studentJson = classToJson.convert((Object)student);
        System.out.println("Converted student to JSON: " + studentJson);
        return studentJson;
    }

    @GetMapping("/3")
    String getMachineJson() {
        Machine machine = machineQuery.byId(1);
        String machineJson = classToJson.convert((Object)machine);
        System.out.println("Converted machine to JSON: " + machineJson);
        return machineJson;
    }

    @GetMapping("/active-bookings/{documentNumber}")
    Object countActiveBookings(@PathVariable String documentNumber) {
        int count = bookingRepository.countActiveByStudent(documentNumber);
        return "El estudiante " + documentNumber + " tiene " + count + " reservas activas (máx: " + com.adanext.NoPainNoMain.config.BookingParameters.MAX_ACTIVE_BOOKINGS_PER_STUDENT + ")";
    }

    @GetMapping("/availability/{machineId}/{date}")
    Object getAvailability(@PathVariable Integer machineId, @PathVariable String date) {
        LocalDate day = LocalDate.parse(date);
        List<TimeSlot> freeSlots = availabilityService.findFreeSlotsByMachine(machineId, day);
        return freeSlots.stream().map(TimeSlot::getName).collect(Collectors.toList());
    }

    @GetMapping("/availability/{date}")
    Object getAllAvailability(@PathVariable String date) {
        LocalDate day = LocalDate.parse(date);
        var allAvailability = availabilityService.findFreeSlotsForAllMachines(day);
        return allAvailability.stream().collect(Collectors.toMap(
            a -> a.getMachine().getName(),
            a -> a.getFreeSlots().stream().map(TimeSlot::getName).collect(Collectors.toList())
        ));
    }
}