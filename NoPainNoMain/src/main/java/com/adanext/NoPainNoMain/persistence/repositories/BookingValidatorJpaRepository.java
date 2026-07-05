package com.adanext.NoPainNoMain.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.entities.BookingValidatorEntity;

public interface BookingValidatorJpaRepository extends JpaRepository<BookingValidatorEntity, String> {
    Optional<BookingValidatorEntity> findByDocumentNumber(String documentNumber);
}