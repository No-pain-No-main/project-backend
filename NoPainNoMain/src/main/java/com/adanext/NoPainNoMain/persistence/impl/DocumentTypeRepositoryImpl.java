package com.adanext.NoPainNoMain.persistence.impl;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.mapper.types.DocumentTypeMapper;
import com.adanext.NoPainNoMain.persistence.types.DocumentTypeEntity;
import com.adanext.NoPainNoMain.persistence.repositories.DocumentTypeJpaRepository;
import com.adanext.NoPainNoMain.domain.repository.DocumentTypeRepository;


@Repository
public class DocumentTypeRepositoryImpl implements DocumentTypeRepository {
    
    private final DocumentTypeJpaRepository repository;

    public DocumentTypeRepositoryImpl(DocumentTypeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public DocumentType save(DocumentType documentType) {
        if (documentType == null) return null;

        DocumentTypeEntity entity = DocumentTypeMapper.toEntity(documentType);
        DocumentTypeEntity saved = repository.save(entity);
        return DocumentTypeMapper.toDomain(saved);
    }

    @Override
    public Optional<DocumentType> findById(Integer id) {
        return repository.findById(id)
            .map(DocumentTypeMapper::toDomain);
    }

    @Override
    public List<DocumentType> findAll() {
        return repository.findAll().stream()
            .map(DocumentTypeMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
