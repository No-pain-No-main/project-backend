package com.adanext.NoPainNoMain.domain.repository;

import com.adanext.NoPainNoMain.domain.types.UserStatus;
import java.util.List;
import java.util.Optional;

public interface UserStatusRepository {
  UserStatus save(UserStatus userStatus);

  Optional<UserStatus> findById(Integer id);

  List<UserStatus> findAll();

  void deleteById(Integer id);
}
