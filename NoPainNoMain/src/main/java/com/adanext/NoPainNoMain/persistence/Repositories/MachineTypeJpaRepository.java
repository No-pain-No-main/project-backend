package com.adanext.NoPainNoMain.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.types.MachineTypeEntity;

public interface MachineTypeJpaRepository extends JpaRepository<MachineTypeEntity, Integer> {
}
