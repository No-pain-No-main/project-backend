package com.adanext.NoPainNoMain.controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.service.query.BookingQuery;
import com.adanext.NoPainNoMain.service.query.StudentQuery;
import com.adanext.NoPainNoMain.service.register.StudentRegister;
import com.adanext.NoPainNoMain.service.update.StudentUpdate;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentQuery studentQuery;
    private final StudentRegister studentRegister;
    private final BookingQuery bookingQuery;
    private final StudentUpdate studentUpdate;
    private final ObjectMapper objectMapper;

    public StudentController(StudentQuery studentQuery, StudentRegister studentRegister,
                              BookingQuery bookingQuery, StudentUpdate studentUpdate,
                              ObjectMapper objectMapper) {
        this.studentQuery = studentQuery;
        this.studentRegister = studentRegister;
        this.bookingQuery = bookingQuery;
        this.studentUpdate = studentUpdate;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<Student> getAll() {
        return studentQuery.findAll();
    }

    @GetMapping("/{documentNumber}")
    public Object getByDocumentNumber(@PathVariable String documentNumber) {
        Student student = studentQuery.studentByDocumentNumber(documentNumber);
        boolean studentNotFound = student == null;
        if (studentNotFound) {
            return "Estudiante con documento " + documentNumber + " no encontrado";
        }
        return student;
    }

    @GetMapping("/active-bookings/{documentNumber}")
    public Object countActiveBookings(@PathVariable String documentNumber) {
        int count = bookingQuery.countActiveByStudent(documentNumber);
        return "El estudiante " + documentNumber + " tiene " + count + " reservas activas";
    }

    @PutMapping("/{documentNumber}")
    public Object updateStudent(@PathVariable String documentNumber, @RequestBody Map<String, Object> body) {
        try {
            String firstName = (String) body.get("firstName");
            String middleName = (String) body.get("middleName");
            String lastName = (String) body.get("lastName");
            String secondLastName = (String) body.get("secondLastName");
            String email = (String) body.get("email");
            Integer statusId = body.get("statusId") != null ? ((Number) body.get("statusId")).intValue() : null;

            Student student = studentUpdate.updateStudent(
                documentNumber, firstName, middleName, lastName, secondLastName,
                email, null, null, null, null, statusId);
            student.setPasswordHash(null);
            return student;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/{documentNumber}")
    public Object deleteStudent(@PathVariable String documentNumber) {
        try {
            studentUpdate.deleteStudent(documentNumber);
            return "Estudiante con documento " + documentNumber + " eliminado correctamente";
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PatchMapping("/{documentNumber}/status")
    public Object updateStatus(@PathVariable String documentNumber, @RequestBody Map<String, Integer> body) {
        try {
            Integer statusId = body.get("statusId");
            if (statusId == null) {
                return "El campo 'statusId' es requerido";
            }
            Student student = studentUpdate.updateStatus(documentNumber, statusId);
            student.setPasswordHash(null);
            return student;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping
    public Object register(@RequestBody Map<String, Object> body) {
        try {
            String json = objectMapper.writeValueAsString(body);
            Student student = studentRegister.save(json);
            student.setPasswordHash(null);
            return student;
        } catch (IllegalStateException | JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
