package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.entities.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotJpaRepository extends JpaRepository<TimeSlotEntity, Integer> {}
