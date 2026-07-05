package com.adanext.NoPainNoMain.persistence.impl;

import com.adanext.NoPainNoMain.domain.repository.MachineStatusRepository;
import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.mapper.types.MachineStatusMapper;
import com.adanext.NoPainNoMain.persistence.repositories.MachineStatusJpaRepository;
import com.adanext.NoPainNoMain.persistence.types.MachineStatusEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class MachineStatusRepositoryImpl implements MachineStatusRepository {

  private final MachineStatusJpaRepository repository;

  public MachineStatusRepositoryImpl(MachineStatusJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public MachineStatus save(MachineStatus machineStatus) {
    if (machineStatus == null) {
      return null;
    }

    MachineStatusEntity entity = MachineStatusMapper.toEntity(machineStatus);
    MachineStatusEntity saved = repository.save(entity);
    return MachineStatusMapper.toDomain(saved);
  }

  @Override
  public Optional<MachineStatus> findById(Integer id) {
    return repository.findById(id).map(MachineStatusMapper::toDomain);
  }

  @Override
  public List<MachineStatus> findAll() {
    return repository.findAll().stream()
        .map(MachineStatusMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteById(Integer id) {
    repository.deleteById(id);
  }
}
