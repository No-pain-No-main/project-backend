package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.PasswordHashHelper;

@Service
public class AdministratorRegister {

    private final JsonToClass<Administrator> jsonToClass;
    private final AdministratorValidator administratorValidator;
    private final AdministratorRepository administratorRepository;
    private final PasswordHashHelper passwordHashHelper;

    public AdministratorRegister(JsonToClass<Administrator> jsonToClass,
                                  AdministratorValidator administratorValidator,
                                  AdministratorRepository administratorRepository,
                                  PasswordHashHelper passwordHashHelper) {
        this.jsonToClass = jsonToClass;
        this.administratorValidator = administratorValidator;
        this.administratorRepository = administratorRepository;
        this.passwordHashHelper = passwordHashHelper;
    }

    public Administrator save(String jsonRegister) {
        Administrator admin = jsonToClass.convert(jsonRegister, Administrator.class);

        administratorValidator.validate(admin);

        String hashed = passwordHashHelper.hashPassword(admin.getPasswordHash());
        admin.registerPassword(hashed);

        return administratorRepository.save(admin);
    }
}