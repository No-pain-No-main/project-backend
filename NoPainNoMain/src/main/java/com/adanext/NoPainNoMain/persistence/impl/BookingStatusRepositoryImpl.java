package com.adanext.NoPainNoMain.persistence.impl;

import com.adanext.NoPainNoMain.domain.repository.BookingStatusRepository;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.mapper.types.BookingStatusMapper;
import com.adanext.NoPainNoMain.persistence.repositories.BookingStatusJpaRepository;
import com.adanext.NoPainNoMain.persistence.types.BookingStatusEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class BookingStatusRepositoryImpl implements BookingStatusRepository {

  private final BookingStatusJpaRepository repository;

  public BookingStatusRepositoryImpl(BookingStatusJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public BookingStatus save(BookingStatus bookingStatus) {
    if (bookingStatus == null) {
      return null;
    }

    BookingStatusEntity entity = BookingStatusMapper.toEntity(bookingStatus);
    BookingStatusEntity saved = repository.save(entity);
    return BookingStatusMapper.toDomain(saved);
  }

  @Override
  public Optional<BookingStatus> findById(Integer id) {
    return repository.findById(id).map(BookingStatusMapper::toDomain);
  }

  @Override
  public List<BookingStatus> findAll() {
    return repository.findAll().stream()
        .map(BookingStatusMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteById(Integer id) {
    repository.deleteById(id);
  }
}
