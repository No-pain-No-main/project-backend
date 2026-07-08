package com.adanext.NoPainNoMain.service.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;
import com.adanext.NoPainNoMain.domain.repository.UserStatusRepository;
import com.adanext.NoPainNoMain.domain.types.UserStatus;
import java.time.LocalDate;
import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.domain.repository.DocumentTypeRepository;
import com.adanext.NoPainNoMain.domain.repository.GenderRepository;

@Service
public class StudentUpdate {

    private final StudentRepository studentRepository;
    private final UserStatusRepository userStatusRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final GenderRepository genderRepository;

    public StudentUpdate(StudentRepository studentRepository,
                         UserStatusRepository userStatusRepository,
                         DocumentTypeRepository documentTypeRepository,
                         GenderRepository genderRepository) {
        this.studentRepository = studentRepository;
        this.userStatusRepository = userStatusRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.genderRepository = genderRepository;
    }

    @Transactional
    public Student updateStatus(String documentNumber, Integer statusId) {
        Student student = studentRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new IllegalStateException("El estudiante con documento " + documentNumber + " no existe"));

        UserStatus newStatus = userStatusRepository.findById(statusId)
            .orElseThrow(() -> new IllegalStateException("El estado de usuario con ID " + statusId + " no existe"));

        student.updateStatus(newStatus);
        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(String documentNumber, String firstName, String middleName,
                                  String lastName, String secondLastName, String email,
                                  Integer documentTypeId, String birthDateStr,
                                  String phone, Integer genderId, Integer statusId) {
        Student student = studentRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new IllegalStateException("El estudiante con documento " + documentNumber + " no existe"));

        if (firstName != null) student.setFirstName(firstName);
        if (middleName != null) student.setMiddleName(middleName);
        if (lastName != null) student.setLastName(lastName);
        if (secondLastName != null) student.setSecondLastName(secondLastName);
        if (email != null) student.setEmail(email);

        if (statusId != null) {
            UserStatus newStatus = userStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalStateException("El estado con ID " + statusId + " no existe"));
            student.updateStatus(newStatus);
        }

        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(String documentNumber) {
        Student student = studentRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new IllegalStateException("El estudiante con documento " + documentNumber + " no existe"));
        studentRepository.deleteByDocumentNumber(documentNumber);
    }
}
