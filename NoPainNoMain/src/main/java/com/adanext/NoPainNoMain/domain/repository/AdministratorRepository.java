package com.adanext.NoPainNoMain.domain.repository;

import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.Administrator;

public interface AdministratorRepository {
    Administrator save(Administrator administrator);
    Optional<Administrator> findById(Integer id);
    Optional<Administrator> findByEmail(String email);
    List<Administrator> findAll();
    void deleteById(Integer id);
}