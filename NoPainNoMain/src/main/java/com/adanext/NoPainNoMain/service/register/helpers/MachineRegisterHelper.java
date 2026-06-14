package com.adanext.NoPainNoMain.service.register.helpers;

import org.springframework.stereotype.Component;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;

@Component
public class MachineRegisterHelper {

    private final MachineRepositoryImpl repository;

    public MachineRegisterHelper(MachineRepositoryImpl repository) {
        this.repository = repository;
    }

    public boolean isDuplicateName(Machine machine) {
        return machine.getName() != null
            && repository.findByName(machine.getName()).isPresent();
    }
}
