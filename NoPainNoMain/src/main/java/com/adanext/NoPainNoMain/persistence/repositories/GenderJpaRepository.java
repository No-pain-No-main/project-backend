package com.adanext.NoPainNoMain.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.types.GenderEntity;

public interface GenderJpaRepository extends JpaRepository<GenderEntity, Integer> {
}
