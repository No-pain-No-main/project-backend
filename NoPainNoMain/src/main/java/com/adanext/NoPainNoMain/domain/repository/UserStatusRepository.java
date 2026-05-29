package com.adanext.NoPainNoMain.domain.repository;
import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.UserStatus;

public interface UserStatusRepository {
    UserStatus save(UserStatus userStatus);
    Optional<UserStatus> findById(Integer id);
    List<UserStatus> findAll();
    void deleteById(Integer id);
    
}