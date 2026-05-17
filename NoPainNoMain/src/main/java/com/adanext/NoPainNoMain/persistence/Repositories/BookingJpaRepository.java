package com.adanext.NoPainNoMain.persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.BookingEntity;

public interface BookingJpaRepository extends JpaRepository<BookingEntity, Integer> {
    List<BookingEntity> findByMachine_Id(Integer machineId);
}
