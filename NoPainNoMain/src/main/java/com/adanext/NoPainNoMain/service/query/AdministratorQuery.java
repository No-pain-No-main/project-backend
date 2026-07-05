package com.adanext.NoPainNoMain.service.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;

@Service
public class AdministratorQuery {

    private final AdministratorRepository administratorRepository;

    public AdministratorQuery(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public Administrator byDocumentNumber(String documentNumber) {
        return administratorRepository.findByDocumentNumber(documentNumber).orElse(null);
    }

    public List<Administrator> findAll() {
        return administratorRepository.findAll();
    }
}