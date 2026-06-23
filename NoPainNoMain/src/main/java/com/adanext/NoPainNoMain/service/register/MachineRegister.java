package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;

@Service
public class MachineRegister {

    private final JsonToClass<Machine> jsonToClass;
    private final MachineRepository machineRepository;

    public MachineRegister(JsonToClass<Machine> jsonToClass, MachineRepository machineRepository) {
        this.jsonToClass = jsonToClass;
        this.machineRepository = machineRepository;
    }

    public Machine save(String jsonRegister) {
        Machine machine = jsonToClass.convert(jsonRegister, Machine.class);

        if (machine.getName() != null
                && machineRepository.findByName(machine.getName()).isPresent()) {
            throw new IllegalStateException("La máquina '" + machine.getName() + "' ya existe en el sistema");
        }

        return machineRepository.save(machine);
    }
}