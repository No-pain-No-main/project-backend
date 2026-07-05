package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.types.MachineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineTypeJpaRepository extends JpaRepository<MachineTypeEntity, Integer> {}
