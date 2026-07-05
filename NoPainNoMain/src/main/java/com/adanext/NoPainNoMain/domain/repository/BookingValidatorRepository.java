package com.adanext.NoPainNoMain.domain.repository;

import java.util.Optional;

import com.adanext.NoPainNoMain.domain.BookingValidator;

public interface BookingValidatorRepository {
    BookingValidator save(BookingValidator validator);
    Optional<BookingValidator> findByDocumentNumber(String documentNumber);
}