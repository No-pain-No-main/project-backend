package com.adanext.NoPainNoMain.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.entities.BookingEntity;

public interface BookingJpaRepository extends JpaRepository<BookingEntity, String> {
    List<BookingEntity> findByMachineId(Integer machineId);
    List<BookingEntity> findByDate(LocalDateTime date);
    List<BookingEntity> findByStudentDocumentNumber(String documentNumber);

    List<BookingEntity> findByMachineIdAndDateBetween(Integer machineId, LocalDateTime start, LocalDateTime end);
    List<BookingEntity> findByDateBetween(LocalDateTime start, LocalDateTime end);

    // Cuenta reservas activas (bookingStatus.id = 1) de un estudiante
    long countByStudentDocumentNumberAndBookingStatusId(String documentNumber, Integer bookingStatusId);
}
