package com.adanext.NoPainNoMain.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.service.query.BookingQuery;
import com.adanext.NoPainNoMain.service.query.StudentQuery;
import com.adanext.NoPainNoMain.service.register.StudentRegister;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentQuery studentQuery;
    private final StudentRegister studentRegister;
    private final BookingQuery bookingQuery;

    public StudentController(StudentQuery studentQuery, StudentRegister studentRegister,
                              BookingQuery bookingQuery) {
        this.studentQuery = studentQuery;
        this.studentRegister = studentRegister;
        this.bookingQuery = bookingQuery;
    }

    @GetMapping
    public List<Student> getAll() {
        return studentQuery.findAll();
    }

    @GetMapping("/{documentNumber}")
    public Object getByDocumentNumber(@PathVariable String documentNumber) {
        Student student = studentQuery.studentByDocumentNumber(documentNumber);
        if (student == null) {
            return "Estudiante con documento " + documentNumber + " no encontrado";
        }
        return student;
    }

    @GetMapping("/active-bookings/{documentNumber}")
    public Object countActiveBookings(@PathVariable String documentNumber) {
        int count = bookingQuery.countActiveByStudent(documentNumber);
        return "El estudiante " + documentNumber + " tiene " + count + " reservas activas";
    }

    @PostMapping
    public Object register(@RequestBody String json) {
        try {
            Student student = studentRegister.save(json);
            return student;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}
