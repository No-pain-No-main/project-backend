package com.adanext.NoPainNoMain.persistence.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;
import com.adanext.NoPainNoMain.mapper.AdministratorMapper;
import com.adanext.NoPainNoMain.persistence.entities.AdministratorEntity;
import com.adanext.NoPainNoMain.persistence.repositories.AdministratorJpaRepository;

@Repository
public class AdministratorRepositoryImpl implements AdministratorRepository {

    private final AdministratorJpaRepository repository;

    public AdministratorRepositoryImpl(AdministratorJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Administrator save(Administrator administrator) {
        if (administrator == null) return null;

        AdministratorEntity entity = AdministratorMapper.toEntity(administrator);
        AdministratorEntity saved = repository.save(entity);
        return AdministratorMapper.toDomain(saved);
    }

    @Override
    public Optional<Administrator> findById(Integer id) {
        return repository.findById(id)
            .map(AdministratorMapper::toDomain);
    }

    @Override
    public Optional<Administrator> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<Administrator> findAll() {
        return repository.findAll().stream()
            .map(AdministratorMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}