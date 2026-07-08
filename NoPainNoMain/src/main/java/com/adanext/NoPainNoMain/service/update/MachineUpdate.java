package com.adanext.NoPainNoMain.service.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import com.adanext.NoPainNoMain.domain.repository.MachineStatusRepository;
import com.adanext.NoPainNoMain.domain.repository.MachineTypeRepository;
import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.domain.types.MachineType;

@Service
public class MachineUpdate {

    private final MachineRepository machineRepository;
    private final MachineStatusRepository machineStatusRepository;
    private final MachineTypeRepository machineTypeRepository;

    public MachineUpdate(MachineRepository machineRepository,
                         MachineStatusRepository machineStatusRepository,
                         MachineTypeRepository machineTypeRepository) {
        this.machineRepository = machineRepository;
        this.machineStatusRepository = machineStatusRepository;
        this.machineTypeRepository = machineTypeRepository;
    }

    @Transactional
    public Machine updateStatus(Integer machineId, Integer statusId) {
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new IllegalStateException("La máquina con ID " + machineId + " no existe"));

        MachineStatus newStatus = machineStatusRepository.findById(statusId)
            .orElseThrow(() -> new IllegalStateException("El estado de máquina con ID " + statusId + " no existe"));

        machine.updateStatus(newStatus);
        return machineRepository.save(machine);
    }

    @Transactional
    public Machine updateMachine(Integer machineId, String name, Integer typeId, Integer statusId) {
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new IllegalStateException("La máquina con ID " + machineId + " no existe"));

        if (name != null) machine.setName(name);

        if (typeId != null) {
            MachineType newType = machineTypeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalStateException("El tipo de máquina con ID " + typeId + " no existe"));
            machine.setMachineType(newType);
        }

        if (statusId != null) {
            MachineStatus newStatus = machineStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalStateException("El estado de máquina con ID " + statusId + " no existe"));
            machine.updateStatus(newStatus);
        }

        return machineRepository.save(machine);
    }

    @Transactional
    public void deleteMachine(Integer machineId) {
        Machine machine = machineRepository.findById(machineId)
            .orElseThrow(() -> new IllegalStateException("La máquina con ID " + machineId + " no existe"));
        machineRepository.deleteById(machineId);
    }
}