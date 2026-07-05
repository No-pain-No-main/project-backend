package com.adanext.NoPainNoMain.domain.repository;

import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import java.util.List;
import java.util.Optional;

public interface BookingStatusRepository {
  BookingStatus save(BookingStatus bookingStatus);

  Optional<BookingStatus> findById(Integer id);

  List<BookingStatus> findAll();

  void deleteById(Integer id);
}
