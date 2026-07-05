package com.adanext.NoPainNoMain.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.types.BookingStatusEntity;

public interface BookingStatusJpaRepository extends JpaRepository<BookingStatusEntity, Integer> {
}
