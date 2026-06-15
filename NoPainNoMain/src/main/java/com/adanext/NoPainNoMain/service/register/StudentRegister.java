package com.adanext.NoPainNoMain.service.register;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.persistence.impl.StudentRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonconverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.StudentRegisterHelper;
import org.springframework.stereotype.Service;

@Service
public class StudentRegister {

  private final JsonToClass<Student> jsonToClass;
  private final StudentRepositoryImpl studentRepositoryImpl;
  private final StudentRegisterHelper helper;

  public StudentRegister(
      JsonToClass<Student> jsonToClass,
      StudentRepositoryImpl studentRepositoryImpl,
      StudentRegisterHelper helper) {
    this.jsonToClass = jsonToClass;
    this.studentRepositoryImpl = studentRepositoryImpl;
    this.helper = helper;
  }

  public Student save(String jsonRegister) {
    Student student = jsonToClass.convert(jsonRegister, Student.class);

    if (helper.isDuplicateDocument(student)) {
      throw new IllegalStateException(
          "El estudiante con documento "
              + student.getDocumentNumber()
              + " ya existe en el sistema");
    }

    if (helper.isDuplicateEmail(student)) {
      throw new IllegalStateException(
          "El email " + student.getEmail() + " ya está registrado por otro estudiante");
    }

    return studentRepositoryImpl.save(student);
  }
}
