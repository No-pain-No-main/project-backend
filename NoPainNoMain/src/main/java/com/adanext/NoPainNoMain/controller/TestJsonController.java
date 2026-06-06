package com.adanext.NoPainNoMain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.AdministratorRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.ClassToJson;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.query.MachineQuery;
import com.adanext.NoPainNoMain.service.query.StudentQuery;
import com.adanext.NoPainNoMain.service.register.BookingRegister;
import com.adanext.NoPainNoMain.service.register.MachineRegister;
import com.adanext.NoPainNoMain.service.register.StudentRegister;


@RestController
@RequestMapping("/api/test")
public class TestJsonController {

    private final ClassToJson classToJson;
    private final JsonToClass jsonToClass;
    private final StudentQuery studentQuery;
    private final StudentRegister studentRegister;
    private final BookingRegister bookingRegister;
    private final MachineRegister machineRegister;
    private final MachineQuery machineQuery;
    private final AdministratorRepositoryImpl administratorRepository;

    public TestJsonController(ClassToJson classToJson, JsonToClass jsonToClass, StudentQuery studentQuery,StudentRegister studentRegister, BookingRegister bookingRegister, MachineRegister machineRegister, MachineQuery machineQuery, AdministratorRepositoryImpl administratorRepository){
        this.classToJson = classToJson;
        this.jsonToClass = jsonToClass;
        this.studentQuery = studentQuery;
        this.studentRegister = studentRegister;
        this.bookingRegister = bookingRegister;
        this.machineRegister = machineRegister;
        this.machineQuery = machineQuery;
        this.administratorRepository = administratorRepository;
    }

    @PostMapping("/booking")
    Object registerBooking(){
        try {
            String json ="""
                        {
                        "id": "1200005151",
                        "date": "2000-05-15T10:00:00",
                        "bookingStatus":  {
                            "id": 1
                        },
                        "machine":  {
                            "id": 1
                        },
                        "student":  {
                            "documentNumber": "12345678"
                        },
                        "timeSlot":  {
                            "id": 1
                        }
                        }
                        """;
            Booking booking = bookingRegister.save(json);
            System.out.println("Registered booking: " + booking.getId());
            return booking;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/machine")
    Object registerMachine(){
        try {
            String json ="""
                        {
                        "id": null,
                        "name": "pruebaMaquina",
                        "machineStatus":  {
                            "id": 1
                        },
                        "machineType":  {
                            "id": 1
                        }
                        }
                        """;
            Machine machine = machineRegister.save(json);
            System.out.println("Registered machine: " + machine.getId());
            return machine;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/student")
    Object registerStudent(){
        try {
            String json ="""
                        {
                        "documentNumber": "12345678",
                        "firstName": "Juan",
                        "middleName": "Carlos",
                        "lastName": "Pérez",
                        "secondLastName": "Gómez",
                        "birthDate": "2000-05-15",
                        "email": "juan.perefcvgbnz@example.com",
                        "passwordHash": "xbbyz123hash",
                        "phone": "+573001234567",
                        "documentType": {
                            "id": 1
                        },
                        "gender": {
                            "id": 2
                        },
                        "userStatus": {
                            "id": 1
                        }
                        }
                        """;
            Student student = studentRegister.save(json);
            System.out.println("Registered student: " + student.getFirstName());
            return student;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }


    @PostMapping("/administrator")
    Object registerAdministrator(){
        try {
            String json ="""
                        {
                        "documentNumber": "98765432",
                        "firstName": "Admin",
                        "middleName": "Sistema",
                        "lastName": "Principal",
                        "secondLastName": "Root",
                        "documentType": {
                            "id": 1
                        },
                        "phone": "+573001234568",
                        "position": "Administrador General",
                        "passwordHash": "adminHash123",
                        "secretPhrase": "fraseSecretaAdmin"
                        }
                        """;
            Administrator admin = (Administrator) jsonToClass.convert(json, Administrator.class);
            // Verificar si ya existe
            if (administratorRepository.findByDocumentNumber(admin.getDocumentNumber()).isPresent()) {
                return "El administrador con documento " + admin.getDocumentNumber() + " ya existe en el sistema";
            }
            Administrator saved = administratorRepository.save(admin);
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

    public static class TestObject {
        private Integer id;
        private String name;

        public TestObject() {}

        public TestObject(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() { return id; }
        public String getName() { return name; }

        public void setId(Integer id) { this.id = id; }
        public void setName(String name) { this.name = name; }
    }
}