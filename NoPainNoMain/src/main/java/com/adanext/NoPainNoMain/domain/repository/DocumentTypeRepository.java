package com.adanext.NoPainNoMain.domain.repository;
import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.DocumentType;

public interface DocumentTypeRepository {
    DocumentType save(DocumentType documentType);
    Optional<DocumentType> findById(Integer id);
    List<DocumentType> findAll();
    void deleteById(Integer id);
}