package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.types.GenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderJpaRepository extends JpaRepository<GenderEntity, Integer> {}
