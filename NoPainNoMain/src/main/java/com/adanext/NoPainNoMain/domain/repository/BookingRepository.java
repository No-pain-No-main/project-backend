package com.adanext.NoPainNoMain.domain.repository;

import java.time.LocalDateTime;
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
    List<Booking> findByDate(LocalDateTime date);
    List<Booking> findByStudent(Student student);
    List<Booking> findByMachineIdAndDateBetween(Integer machineId, LocalDateTime start, LocalDateTime end);
    List<Booking> findByDateBetween(LocalDateTime start, LocalDateTime end);
    int countActiveByStudent(String documentNumber);
}