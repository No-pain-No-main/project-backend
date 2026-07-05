package com.adanext.NoPainNoMain.persistence.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.BookingValidator;
import com.adanext.NoPainNoMain.domain.repository.BookingValidatorRepository;
import com.adanext.NoPainNoMain.mapper.BookingValidatorMapper;
import com.adanext.NoPainNoMain.persistence.entities.BookingValidatorEntity;
import com.adanext.NoPainNoMain.persistence.repositories.BookingValidatorJpaRepository;

@Repository
public class BookingValidatorRepositoryImpl implements BookingValidatorRepository {

    private final BookingValidatorJpaRepository repository;

    public BookingValidatorRepositoryImpl(BookingValidatorJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookingValidator save(BookingValidator validator) {
        if (validator == null) return null;

        BookingValidatorEntity entity = BookingValidatorMapper.toEntity(validator);
        BookingValidatorEntity saved = repository.save(entity);
        return BookingValidatorMapper.toDomain(saved);
    }

    @Override
    public Optional<BookingValidator> findByDocumentNumber(String documentNumber) {
        return repository.findByDocumentNumber(documentNumber)
                .map(BookingValidatorMapper::toDomain);
    }
}