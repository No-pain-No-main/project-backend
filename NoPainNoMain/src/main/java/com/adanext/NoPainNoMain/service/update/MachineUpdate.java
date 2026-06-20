package com.adanext.NoPainNoMain.service.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.MachineStatusRepositoryImpl;

@Service
public class MachineUpdate {

    private final MachineRepositoryImpl machineRepository;
    private final MachineStatusRepositoryImpl machineStatusRepository;

    public MachineUpdate(MachineRepositoryImpl machineRepository,
                         MachineStatusRepositoryImpl machineStatusRepository) {
        this.machineRepository = machineRepository;
        this.machineStatusRepository = machineStatusRepository;
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
}