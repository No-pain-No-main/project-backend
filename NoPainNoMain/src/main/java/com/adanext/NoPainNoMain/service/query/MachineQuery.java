package com.adanext.NoPainNoMain.service.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;

@Service
public class MachineQuery {

    private final MachineRepository machineRepository;

    public MachineQuery(MachineRepository machineRepository) {
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