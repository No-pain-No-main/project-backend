package com.adanext.NoPainNoMain.domain.repository;
import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.BookingStatus;

public interface BookingStatusRepository {
    BookingStatus save(BookingStatus bookingStatus);
    Optional<BookingStatus> findById(Integer id);
    List<BookingStatus> findAll();
    void deleteById(Integer id);
    
}