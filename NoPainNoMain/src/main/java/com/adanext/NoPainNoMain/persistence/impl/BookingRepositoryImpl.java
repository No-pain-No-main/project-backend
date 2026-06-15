package com.adanext.NoPainNoMain.persistence.impl;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.mapper.BookingMapper;
import com.adanext.NoPainNoMain.persistence.entities.BookingEntity;
import com.adanext.NoPainNoMain.persistence.repositories.BookingJpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

  private final BookingJpaRepository repository;

  public BookingRepositoryImpl(BookingJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public Booking save(Booking booking) {
    if (booking == null) {
      return null;
    }

    BookingEntity entity = BookingMapper.toEntity(booking);
    BookingEntity saved = repository.save(entity);
    return BookingMapper.toDomain(saved);
  }

  @Override
  public Optional<Booking> findById(String id) {
    return repository.findById(id).map(BookingMapper::toDomain);
  }

  @Override
  public List<Booking> findAll() {
    return repository.findAll().stream().map(BookingMapper::toDomain).collect(Collectors.toList());
  }

  @Override
  public List<Booking> findByMachineId(Integer machineId) {
    return repository.findByMachineId(machineId).stream()
        .map(BookingMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Booking> findByDate(LocalDate date) {
    return repository.findByDate(date).stream()
        .map(BookingMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Booking> findByStudent(Student student) {
    if (student == null || student.getDocumentNumber() == null) {
      return List.of();
    }
    return repository.findByStudentDocumentNumber(student.getDocumentNumber()).stream()
        .map(BookingMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Booking> findByMachineIdAndDateBetween(
      Integer machineId, LocalDate start, LocalDate end) {
    return repository.findByMachineIdAndDateBetween(machineId, start, end).stream()
        .map(BookingMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Booking> findByDateBetween(LocalDate start, LocalDate end) {
    return repository.findByDateBetween(start, end).stream()
        .map(BookingMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public int countActiveByStudent(String documentNumber) {
    return (int) repository.countByStudentDocumentNumberAndBookingStatusId(documentNumber, 1);
  }

  @Override
  public void deleteById(String id) {
    repository.deleteById(id);
  }
}
