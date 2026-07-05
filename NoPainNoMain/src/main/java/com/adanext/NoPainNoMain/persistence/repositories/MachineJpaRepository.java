package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.entities.MachineEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineJpaRepository extends JpaRepository<MachineEntity, Integer> {
  Optional<MachineEntity> findByName(String name);
}
