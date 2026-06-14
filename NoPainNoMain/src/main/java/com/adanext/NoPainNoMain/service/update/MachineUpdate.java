package com.adanext.NoPainNoMain.service.update;

import org.springframework.stereotype.Service;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.service.update.helpers.MachineUpdateHelper;

@Service
public class MachineUpdate {

    private final MachineUpdateHelper helper;

    public MachineUpdate(MachineUpdateHelper helper) {
        this.helper = helper;
    }

    public Machine updateStatus(Integer machineId, Integer statusId) {
        Machine machine = helper.findMachine(machineId);
        return helper.updateStatus(machine, statusId);
    }

}