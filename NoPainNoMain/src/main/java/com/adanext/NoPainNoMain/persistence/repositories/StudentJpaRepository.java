package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.entities.StudentEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, String> {
  Optional<StudentEntity> findByEmail(String email);

  Optional<StudentEntity> findByDocumentNumber(String documentNumber);
}
