package com.adanext.NoPainNoMain.persistence.impl;

import com.adanext.NoPainNoMain.domain.repository.MachineTypeRepository;
import com.adanext.NoPainNoMain.domain.types.MachineType;
import com.adanext.NoPainNoMain.mapper.types.MachineTypeMapper;
import com.adanext.NoPainNoMain.persistence.repositories.MachineTypeJpaRepository;
import com.adanext.NoPainNoMain.persistence.types.MachineTypeEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class MachineTypeRepositoryImpl implements MachineTypeRepository {

  private final MachineTypeJpaRepository repository;

  public MachineTypeRepositoryImpl(MachineTypeJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public MachineType save(MachineType machineType) {
    if (machineType == null) {
      return null;
    }

    MachineTypeEntity entity = MachineTypeMapper.toEntity(machineType);
    MachineTypeEntity saved = repository.save(entity);
    return MachineTypeMapper.toDomain(saved);
  }

  @Override
  public Optional<MachineType> findById(Integer id) {
    return repository.findById(id).map(MachineTypeMapper::toDomain);
  }

  @Override
  public List<MachineType> findAll() {
    return repository.findAll().stream()
        .map(MachineTypeMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteById(Integer id) {
    repository.deleteById(id);
  }
}
