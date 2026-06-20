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
import com.adanext.NoPainNoMain.service.jsonConverter.ClassToJson;
import com.adanext.NoPainNoMain.service.query.AdministratorQuery;
import com.adanext.NoPainNoMain.service.query.AvailabilityService;
import com.adanext.NoPainNoMain.service.query.BookingQuery;
import com.adanext.NoPainNoMain.service.query.MachineQuery;
import com.adanext.NoPainNoMain.service.query.StudentQuery;
import com.adanext.NoPainNoMain.service.register.AdministratorRegister;
import com.adanext.NoPainNoMain.service.register.BookingRegister;
import com.adanext.NoPainNoMain.service.register.MachineRegister;
import com.adanext.NoPainNoMain.service.register.StudentRegister;
import com.adanext.NoPainNoMain.service.update.BookingCancelService;
import com.adanext.NoPainNoMain.service.update.BookingConfirmService;
import com.adanext.NoPainNoMain.service.update.MachineUpdate;


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
    private final AdministratorQuery administratorQuery;
    private final BookingQuery bookingQuery;
    private final AvailabilityService availabilityService;
    private final MachineUpdate machineUpdateService;
    private final BookingCancelService bookingCancelService;
    private final BookingConfirmService bookingConfirmService;

    public TestJsonController(ClassToJson classToJson,
                              StudentQuery studentQuery,
                              StudentRegister studentRegister,
                              BookingRegister bookingRegister,
                              MachineRegister machineRegister,
                              MachineQuery machineQuery,
                              AdministratorRegister administratorRegister,
                              AdministratorQuery administratorQuery,
                              BookingQuery bookingQuery,
                              AvailabilityService availabilityService,
                              MachineUpdate machineUpdateService,
                              BookingCancelService bookingCancelService,
                              BookingConfirmService bookingConfirmService){
        this.classToJson = classToJson;
        this.studentQuery = studentQuery;
        this.studentRegister = studentRegister;
        this.bookingRegister = bookingRegister;
        this.machineRegister = machineRegister;
        this.machineQuery = machineQuery;
        this.administratorRegister = administratorRegister;
        this.administratorQuery = administratorQuery;
        this.bookingQuery = bookingQuery;
        this.availabilityService = availabilityService;
        this.machineUpdateService = machineUpdateService;
        this.bookingCancelService = bookingCancelService;
        this.bookingConfirmService = bookingConfirmService;
    }

    // ─── GET endpoints (consulta por ID) ─────────────────────────

    @GetMapping("/student/{documentNumber}")
    Object getStudent(@PathVariable String documentNumber) {
        Student student = studentQuery.studentByDocumentNumber(documentNumber);
        if (student == null) {
            return "Estudiante con documento " + documentNumber + " no encontrado";
        }
        return student;
    }

    @GetMapping("/administrator/{documentNumber}")
    Object getAdministrator(@PathVariable String documentNumber) {
        Administrator admin = administratorQuery.byDocumentNumber(documentNumber);
        if (admin == null) {
            return "Administrador con documento " + documentNumber + " no encontrado";
        }
        return admin;
    }

    @GetMapping("/machine/{machineId}")
    Object getMachine(@PathVariable Integer machineId) {
        Machine machine = machineQuery.byId(machineId);
        if (machine == null) {
            return "Máquina con ID " + machineId + " no encontrada";
        }
        return machine;
    }

    @GetMapping("/booking/{bookingId}")
    Object getBooking(@PathVariable String bookingId) {
        Booking booking = bookingQuery.byId(bookingId);
        if (booking == null) {
            return "Reserva con ID " + bookingId + " no encontrada";
        }
        return booking;
    }

    // ─── GET endpoints (listados) ────────────────────────────────

    @GetMapping("/students")
    List<Student> getAllStudents() {
        return studentQuery.findAll();
    }

    @GetMapping("/machines")
    List<Machine> getAllMachines() {
        return machineQuery.findAll();
    }

    @GetMapping("/administrators")
    List<Administrator> getAllAdministrators() {
        return administratorQuery.findAll();
    }

    // ─── POST endpoints (registro) ───────────────────────────────

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

    @PostMapping("/machine/{machineId}/status/{statusId}")
    Object updateMachineStatus(@PathVariable Integer machineId, @PathVariable Integer statusId){
        try {
            Machine machine = machineUpdateService.updateStatus(machineId, statusId);
            System.out.println("Updated machine " + machineId + " status to " + statusId);
            return machine;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/booking/{bookingId}/cancel")
    Object cancelBooking(@PathVariable String bookingId){
        try {
            Booking booking = bookingCancelService.cancel(bookingId);
            System.out.println("Cancelled booking: " + booking.getId());
            return booking;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/booking/confirm/{studentDocumentNumber}")
    Object confirmBooking(@PathVariable String studentDocumentNumber){
        try {
            Booking booking = bookingConfirmService.confirm(studentDocumentNumber);
            System.out.println("Confirmed booking: " + booking.getId());
            return booking;
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

    // ─── Reportes y consultas específicas ────────────────────────

    @GetMapping("/active-bookings/{documentNumber}")
    Object countActiveBookings(@PathVariable String documentNumber) {
        int count = bookingQuery.countActiveByStudent(documentNumber);
        return "El estudiante " + documentNumber + " tiene " + count + " reservas activas";
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