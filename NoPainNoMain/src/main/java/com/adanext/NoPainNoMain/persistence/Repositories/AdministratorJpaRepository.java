package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.entities.AdministratorEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorJpaRepository extends JpaRepository<AdministratorEntity, String> {
  Optional<AdministratorEntity> findByEmail(String email);

  Optional<AdministratorEntity> findByDocumentNumber(String documentNumber);

  void deleteByDocumentNumber(String documentNumber);
}
