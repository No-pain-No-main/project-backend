package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.types.MachineStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineStatusJpaRepository extends JpaRepository<MachineStatusEntity, Integer> {}
