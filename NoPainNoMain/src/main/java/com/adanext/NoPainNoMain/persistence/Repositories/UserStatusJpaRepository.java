package com.adanext.NoPainNoMain.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adanext.NoPainNoMain.persistence.types.UserStatusEntity;

public interface UserStatusJpaRepository extends JpaRepository<UserStatusEntity, Integer> {
}
