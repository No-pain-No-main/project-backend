package com.adanext.NoPainNoMain.persistence.impl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.domain.repository.TimeSlotRepository;
import com.adanext.NoPainNoMain.mapper.TimeSlotMapper;
import com.adanext.NoPainNoMain.persistence.entities.TimeSlotEntity;
import com.adanext.NoPainNoMain.persistence.repositories.TimeSlotJpaRepository;


@Repository
public class TimeSlotRepositoryImpl implements TimeSlotRepository {
    
    private final TimeSlotJpaRepository repository;

    public TimeSlotRepositoryImpl(TimeSlotJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public TimeSlot save(TimeSlot timeSlot) {
        if (timeSlot == null) return null;

        TimeSlotEntity entity = TimeSlotMapper.toEntity(timeSlot);
        TimeSlotEntity saved = repository.save(entity);
        return TimeSlotMapper.toDomain(saved);
    }

    @Override
    public Optional<TimeSlot> findById(Integer id) {
        return repository.findById(id)
            .map(TimeSlotMapper::toDomain);
    }

    @Override
    public List<TimeSlot> findAll() {
        return repository.findAll().stream()
            .map(TimeSlotMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}