package com.adanext.NoPainNoMain.service.register;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;

@Service
public class MachineRegister {

    private final JsonToClass<Machine> jsonToClass;
    private final MachineRepositoryImpl machineRepositoryImpl;

    // Eliminamos el atributo global 'machine' del constructor
    public MachineRegister(JsonToClass<Machine> jsonToClass, MachineRepositoryImpl machineRepositoryImpl) {
        this.jsonToClass = jsonToClass;
        this.machineRepositoryImpl = machineRepositoryImpl;
    }

    public Machine save(String jsonRegister) {
        // 1. Convertimos el JSON a objeto de dominio (variable local)
        Machine machine = jsonToClass.convert(jsonRegister, Machine.class);
        
        // 2. Verificamos si ya existe una máquina con el mismo nombre
        if (machineRepositoryImpl.findByName(machine.getName()).isPresent()) {
            throw new IllegalStateException("La máquina '" + machine.getName() + "' ya existe en el sistema");
        }
        
        // 3. Guardamos en el repositorio y retornamos
        return machineRepositoryImpl.save(machine);
    }
}