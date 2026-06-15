package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.types.BookingStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingStatusJpaRepository extends JpaRepository<BookingStatusEntity, Integer> {}
