package com.adanext.NoPainNoMain.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;

public interface BookingRepository {
    Booking save(Booking booking);
    Optional<Booking> findById(String id);
    List<Booking> findAll();
    void deleteById(String id);
    List<Booking> findByMachineId(Integer machineId);
    List<Booking> findByDate(LocalDate date);
    List<Booking> findByStudent(Student student);
    List<Booking> findByStudentDocumentNumber(String documentNumber);
    List<Booking> findByMachineIdAndDateBetween(Integer machineId, LocalDate start, LocalDate end);
    List<Booking> findByDateBetween(LocalDate start, LocalDate end);
    int countActiveByStudent(String documentNumber);
}
