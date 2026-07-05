package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.types.DocumentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentTypeJpaRepository extends JpaRepository<DocumentTypeEntity, Integer> {}
