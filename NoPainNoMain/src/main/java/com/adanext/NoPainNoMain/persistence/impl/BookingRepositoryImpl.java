package com.adanext.NoPainNoMain.persistence.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.mapper.BookingMapper;
import com.adanext.NoPainNoMain.persistence.entities.BookingEntity;
import com.adanext.NoPainNoMain.persistence.Repositories.BookingJpaRepository;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingJpaRepository repository;

    public BookingRepositoryImpl(BookingJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Booking save(Booking booking) {
        if (booking == null) return null;

        BookingEntity entity = BookingMapper.toEntity(booking);
        BookingEntity saved = repository.save(entity);
        return BookingMapper.toDomain(saved);
    }

    @Override
    public Optional<Booking> findById(Integer id) {
        return repository.findById(id)
            .map(BookingMapper::toDomain);
    }

    @Override
    public List<Booking> findAll() {
        return repository.findAll().stream()
            .map(BookingMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByMachineId(Integer machineId) {
        return repository.findByMachine_Id(machineId).stream()
            .map(BookingMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}