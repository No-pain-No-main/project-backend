package com.adanext.NoPainNoMain.persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.entities.AdministratorEntity;

public interface AdministratorJpaRepository extends JpaRepository<AdministratorEntity, Integer> {
}
