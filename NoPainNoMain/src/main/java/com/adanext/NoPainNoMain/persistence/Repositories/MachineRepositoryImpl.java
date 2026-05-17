package com.adanext.NoPainNoMain.persistence.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import com.adanext.NoPainNoMain.persistence.MachineEntity;
import com.adanext.NoPainNoMain.persistence.mappers.MachineMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class MachineRepositoryImpl implements MachineRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Machine save(Machine machine) {
        if (machine == null) return null;

        MachineEntity entity = MachineMapper.toEntity(machine);
        if (machine.getId() == null) {
            entityManager.persist(entity);
            entityManager.flush();
            return MachineMapper.toDomain(entity);
        }

        MachineEntity merged = entityManager.merge(entity);
        return MachineMapper.toDomain(merged);
    }

    @Override
    public Optional<Machine> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(MachineEntity.class, id))
            .map(MachineMapper::toDomain);
    }

    @Override
    public List<Machine> findAll() {
        List<MachineEntity> entities = entityManager
            .createQuery("SELECT m FROM MachineEntity m", MachineEntity.class)
            .getResultList();

        return entities.stream()
            .map(MachineMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        MachineEntity entity = entityManager.find(MachineEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}