package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;

@Service
public class MachineRegister {

    private final JsonToClass<Machine> jsonToClass;
    private final MachineRepositoryImpl machineRepositoryImpl;

    public MachineRegister(JsonToClass<Machine> jsonToClass, MachineRepositoryImpl machineRepositoryImpl) {
        this.jsonToClass = jsonToClass;
        this.machineRepositoryImpl = machineRepositoryImpl;
    }

    public Machine save(String jsonRegister) {
        Machine machine = jsonToClass.convert(jsonRegister, Machine.class);
        
        if (machineRepositoryImpl.findByName(machine.getName()).isPresent()) {
            throw new IllegalStateException("La máquina '" + machine.getName() + "' ya existe en el sistema");
        }
        
        return machineRepositoryImpl.save(machine);
    }
}