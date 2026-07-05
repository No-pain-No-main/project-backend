package com.adanext.NoPainNoMain.domain.repository;

import com.adanext.NoPainNoMain.domain.Administrator;
import java.util.List;
import java.util.Optional;

public interface AdministratorRepository {
  Administrator save(Administrator administrator);

  Optional<Administrator> findById(String id);

  Optional<Administrator> findByEmail(String email);

  List<Administrator> findAll();

  void deleteById(String id);
}
