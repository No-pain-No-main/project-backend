package com.adanext.NoPainNoMain.persistence.impl;

import com.adanext.NoPainNoMain.domain.repository.GenderRepository;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.mapper.types.GenderMapper;
import com.adanext.NoPainNoMain.persistence.repositories.GenderJpaRepository;
import com.adanext.NoPainNoMain.persistence.types.GenderEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class GenderRepositoryImpl implements GenderRepository {

  private final GenderJpaRepository repository;

  public GenderRepositoryImpl(GenderJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public Gender save(Gender gender) {
    if (gender == null) {
      return null;
    }

    GenderEntity entity = GenderMapper.toEntity(gender);
    GenderEntity saved = repository.save(entity);
    return GenderMapper.toDomain(saved);
  }

  @Override
  public Optional<Gender> findById(Integer id) {
    return repository.findById(id).map(GenderMapper::toDomain);
  }

  @Override
  public List<Gender> findAll() {
    return repository.findAll().stream().map(GenderMapper::toDomain).collect(Collectors.toList());
  }

  @Override
  public void deleteById(Integer id) {
    repository.deleteById(id);
  }
}
