package com.adanext.NoPainNoMain.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.entities.TimeSlotEntity;


public interface TimeSlotJpaRepository extends JpaRepository<TimeSlotEntity, Integer> {
    
}