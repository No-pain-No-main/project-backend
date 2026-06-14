package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.MachineRegisterHelper;

@Service
public class MachineRegister {

    private final JsonToClass<Machine> jsonToClass;
    private final MachineRepositoryImpl machineRepositoryImpl;
    private final MachineRegisterHelper helper;

    public MachineRegister(JsonToClass<Machine> jsonToClass, MachineRepositoryImpl machineRepositoryImpl,
                           MachineRegisterHelper helper) {
        this.jsonToClass = jsonToClass;
        this.machineRepositoryImpl = machineRepositoryImpl;
        this.helper = helper;
    }

    public Machine save(String jsonRegister) {
        Machine machine = jsonToClass.convert(jsonRegister, Machine.class);

        if (helper.isDuplicateName(machine)) {
            throw new IllegalStateException("La máquina '" + machine.getName() + "' ya existe en el sistema");
        }

        return machineRepositoryImpl.save(machine);
    }
}