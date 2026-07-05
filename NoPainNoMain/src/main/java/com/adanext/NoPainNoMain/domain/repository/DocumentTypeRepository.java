package com.adanext.NoPainNoMain.domain.repository;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import java.util.List;
import java.util.Optional;

public interface DocumentTypeRepository {
  DocumentType save(DocumentType documentType);

  Optional<DocumentType> findById(Integer id);

  List<DocumentType> findAll();

  void deleteById(Integer id);
}
