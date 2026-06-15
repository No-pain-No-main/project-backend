package com.adanext.NoPainNoMain.service.register.helpers;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;
import org.springframework.stereotype.Component;

@Component
public class AdminRegisterHelper {

  private final AdministratorRepository repository;

  public AdminRegisterHelper(AdministratorRepository repository) {
    this.repository = repository;
  }

  public boolean isDuplicateDocument(Administrator admin) {
    return admin.getDocumentNumber() != null
        && repository.findById(admin.getDocumentNumber()).isPresent();
  }

  public boolean isDuplicateEmail(Administrator admin) {
    return admin.getEmail() != null && repository.findByEmail(admin.getEmail()).isPresent();
  }
}
