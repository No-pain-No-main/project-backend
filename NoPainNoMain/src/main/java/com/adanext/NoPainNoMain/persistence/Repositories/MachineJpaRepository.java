package com.adanext.NoPainNoMain.persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.MachineEntity;

public interface MachineJpaRepository extends JpaRepository<MachineEntity, Integer> {
}
