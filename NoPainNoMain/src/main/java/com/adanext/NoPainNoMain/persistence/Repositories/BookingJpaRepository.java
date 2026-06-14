package com.adanext.NoPainNoMain.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.entities.BookingEntity;

public interface BookingJpaRepository extends JpaRepository<BookingEntity, String> {
    List<BookingEntity> findByMachineId(Integer machineId);
    List<BookingEntity> findByDate(LocalDate date);
    List<BookingEntity> findByStudentDocumentNumber(String documentNumber);

    List<BookingEntity> findByMachineIdAndDateBetween(Integer machineId, LocalDate start, LocalDate end);
    List<BookingEntity> findByDateBetween(LocalDate start, LocalDate end);

    // Cuenta reservas activas (bookingStatus.id = 1) de un estudiante
    long countByStudentDocumentNumberAndBookingStatusId(String documentNumber, Integer bookingStatusId);
}
