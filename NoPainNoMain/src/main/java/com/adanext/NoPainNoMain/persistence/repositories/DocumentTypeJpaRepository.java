package com.adanext.NoPainNoMain.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.types.DocumentTypeEntity;

public interface DocumentTypeJpaRepository extends JpaRepository<DocumentTypeEntity, Integer> {
}
