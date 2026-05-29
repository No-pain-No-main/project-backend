package com.adanext.NoPainNoMain.domain.repository;
import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.Gender;

public interface GenderRepository {
    Gender save(Gender gender);
    Optional<Gender> findById(Integer id);
    List<Gender> findAll();
    void deleteById(Integer id);
}