package com.adanext.NoPainNoMain.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.entities.MachineEntity;

public interface MachineJpaRepository extends JpaRepository<MachineEntity, Integer> {
    Optional<MachineEntity> findByName(String name);
}
