package com.adanext.NoPainNoMain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.service.jsonConverter.ClassToJson;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.query.StudentQuery;
import com.adanext.NoPainNoMain.service.register.StudentRegister;

@RestController
@RequestMapping("/api/test")
public class TestJsonController {

    private final ClassToJson classToJson;
    private final JsonToClass jsonToClass;
    private final StudentQuery studentQuery;
    private final StudentRegister studentRegister;


    public TestJsonController(ClassToJson classToJson, JsonToClass jsonToClass, StudentQuery studentQuery,StudentRegister studentRegister) {
        this.classToJson = classToJson;
        this.jsonToClass = jsonToClass;
        this.studentQuery = studentQuery;
        this.studentRegister = studentRegister;
    }

    @PostMapping("/student")
    Student registerStudent(){
        String json ="""
                    {
                    "id": 0,
                    "firstName": "Juan",
                    "middleName": "Carlos",
                    "lastName": "Pérez",
                    "secondLastName": "Gómez",
                    "birthDate": "2000-05-15",
                    "documentNumber": "123456789",
                    "email": "juan.perez@example.com",
                    "passwordHash": "xyz123hash",
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
        Student student =studentRegister.save(json);
        System.out.println("Registered student: " + student.getFirstName() );
        return student;
    }


    @GetMapping("/2")
    String getTestJson() {
        Student student = studentQuery.studentByID(1);
        String studentJson = classToJson.convert((Object)student);
        System.out.println("Converted student to JSON: " + studentJson);
        return studentJson;

           

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