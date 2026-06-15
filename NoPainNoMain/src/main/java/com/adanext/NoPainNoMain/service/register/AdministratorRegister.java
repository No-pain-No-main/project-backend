package com.adanext.NoPainNoMain.service.register;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.persistence.impl.AdministratorRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonconverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.AdminRegisterHelper;
import org.springframework.stereotype.Service;

@Service
public class AdministratorRegister {

  private final JsonToClass<Administrator> jsonToClass;
  private final AdministratorRepositoryImpl administratorRepository;
  private final AdminRegisterHelper helper;

  public AdministratorRegister(
      JsonToClass<Administrator> jsonToClass,
      AdministratorRepositoryImpl administratorRepository,
      AdminRegisterHelper helper) {
    this.jsonToClass = jsonToClass;
    this.administratorRepository = administratorRepository;
    this.helper = helper;
  }

  public Administrator save(String jsonRegister) {
    Administrator admin = jsonToClass.convert(jsonRegister, Administrator.class);

    if (helper.isDuplicateDocument(admin)) {
      throw new IllegalStateException(
          "El administrador con documento "
              + admin.getDocumentNumber()
              + " ya existe en el sistema");
    }

    if (helper.isDuplicateEmail(admin)) {
      throw new IllegalStateException(
          "El email " + admin.getEmail() + " ya está registrado por otro administrador");
    }

    return administratorRepository.save(admin);
  }
}
