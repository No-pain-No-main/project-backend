package com.adanext.NoPainNoMain.persistence.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import com.adanext.NoPainNoMain.mapper.MachineMapper;
import com.adanext.NoPainNoMain.persistence.entities.MachineEntity;
import com.adanext.NoPainNoMain.persistence.repositories.MachineJpaRepository;


@Repository
public class MachineRepositoryImpl implements MachineRepository {

    private final MachineJpaRepository repository;

    public MachineRepositoryImpl(MachineJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Machine save(Machine machine) {
        if (machine == null) return null;

        MachineEntity entity = MachineMapper.toEntity(machine);
        MachineEntity saved = repository.save(entity);
        return MachineMapper.toDomain(saved);
    }

    @Override
    public Optional<Machine> findById(Integer id) {
        return repository.findById(id)
            .map(MachineMapper::toDomain);
    }

    @Override
    public List<Machine> findAll() {
        return repository.findAll().stream()
            .map(MachineMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}