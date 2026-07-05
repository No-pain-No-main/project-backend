package com.adanext.NoPainNoMain.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.types.MachineStatusEntity;

public interface MachineStatusJpaRepository extends JpaRepository<MachineStatusEntity, Integer> {
}
