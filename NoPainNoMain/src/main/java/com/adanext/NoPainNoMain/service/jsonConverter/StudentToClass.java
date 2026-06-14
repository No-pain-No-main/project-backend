package com.adanext.NoPainNoMain.service.jsonConverter;
import com.adanext.NoPainNoMain.domain.Student;

public class StudentToClass {
    private final JsonToClass<Student> jsonToClass;

    public StudentToClass(JsonToClass<Student> jsonToClass) {
        this.jsonToClass = jsonToClass;
    }
    public Student convert(String jsonRegister) {
        Student temporaryStudent = jsonToClass.convert(jsonRegister, Student.class);
        return new Student(
            temporaryStudent.getDocumentNumber(), // documentNumber es ahora la PK
            temporaryStudent.getFirstName(),
            temporaryStudent.getMiddleName(),
            temporaryStudent.getLastName(),
            temporaryStudent.getSecondLastName(),
            temporaryStudent.getEmail(),
            temporaryStudent.getDocumentType(),
            temporaryStudent.getBirthDate(),
            temporaryStudent.getPhone(),
            temporaryStudent.getGender(),
            temporaryStudent.getUserStatus(),
            temporaryStudent.getPasswordHash()  
        );
        
    }

}
