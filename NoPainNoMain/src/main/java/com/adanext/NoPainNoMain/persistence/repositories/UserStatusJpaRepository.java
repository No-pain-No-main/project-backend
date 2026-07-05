package com.adanext.NoPainNoMain.persistence.repositories;

import com.adanext.NoPainNoMain.persistence.types.UserStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusJpaRepository extends JpaRepository<UserStatusEntity, Integer> {}
