package com.adanext.NoPainNoMain.service.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;

@Service
public class MachineQuery {

    private final MachineRepositoryImpl machineRepository;

    public MachineQuery(MachineRepositoryImpl machineRepository) {
        this.machineRepository = machineRepository;
    }

    public Machine byId(Integer machineId) {
        return machineRepository.findById(machineId).orElse(null);
    }

    public Machine byName(String machineName) {
        return machineRepository.findByName(machineName).orElse(null);
    }

    public List<Machine> findAll() {
        return machineRepository.findAll();
    }
}
