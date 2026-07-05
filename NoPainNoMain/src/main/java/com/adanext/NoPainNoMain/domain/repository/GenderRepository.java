package com.adanext.NoPainNoMain.domain.repository;

import com.adanext.NoPainNoMain.domain.types.Gender;
import java.util.List;
import java.util.Optional;

public interface GenderRepository {
  Gender save(Gender gender);

  Optional<Gender> findById(Integer id);

  List<Gender> findAll();

  void deleteById(Integer id);
}
