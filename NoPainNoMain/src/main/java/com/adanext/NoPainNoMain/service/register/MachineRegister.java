package com.adanext.NoPainNoMain.service.register;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import com.adanext.NoPainNoMain.domain.repository.MachineStatusRepository;
import com.adanext.NoPainNoMain.domain.repository.MachineTypeRepository;
import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.domain.types.MachineType;
import com.adanext.NoPainNoMain.service.jsonConverter.JsonToClass;
import org.springframework.stereotype.Service;

@Service
public class MachineRegister {

    private final JsonToClass<Machine> jsonToClass;
    private final MachineRepository machineRepository;
    private final MachineStatusRepository machineStatusRepository;
    private final MachineTypeRepository machineTypeRepository;

    public MachineRegister(JsonToClass<Machine> jsonToClass,
                           MachineRepository machineRepository,
                           MachineStatusRepository machineStatusRepository,
                           MachineTypeRepository machineTypeRepository) {
        this.jsonToClass = jsonToClass;
        this.machineRepository = machineRepository;
        this.machineStatusRepository = machineStatusRepository;
        this.machineTypeRepository = machineTypeRepository;
    }

  public Machine save(String jsonRegister) {
    Machine machine = jsonToClass.convert(jsonRegister, Machine.class);

        if (machine.getName() != null
                && machineRepository.findByName(machine.getName()).isPresent()) {
            throw new IllegalStateException("La máquina '" + machine.getName() + "' ya existe en el sistema");
        }

        // Validar campos obligatorios
        if (machine.getMachineStatus() == null || machine.getMachineStatus().getId() == null) {
            throw new IllegalStateException("El estado de la máquina (machineStatus) es obligatorio");
        }
        if (machine.getMachineType() == null || machine.getMachineType().getId() == null) {
            throw new IllegalStateException("El tipo de la máquina (machineType) es obligatorio");
        }

        // Resolver referencias desde la BD para evitar valores nulos en la persistencia
        MachineStatus resolvedStatus = machineStatusRepository
            .findById(machine.getMachineStatus().getId())
            .orElseThrow(() -> new IllegalStateException(
                "El estado de máquina con ID " + machine.getMachineStatus().getId() + " no existe"));
        machine.setMachineStatus(resolvedStatus);

        MachineType resolvedType = machineTypeRepository
            .findById(machine.getMachineType().getId())
            .orElseThrow(() -> new IllegalStateException(
                "El tipo de máquina con ID " + machine.getMachineType().getId() + " no existe"));
        machine.setMachineType(resolvedType);

        return machineRepository.save(machine);
    }
}
