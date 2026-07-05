package com.adanext.NoPainNoMain.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.service.query.MachineQuery;
import com.adanext.NoPainNoMain.service.register.MachineRegister;
import com.adanext.NoPainNoMain.service.update.MachineUpdate;

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineQuery machineQuery;
    private final MachineRegister machineRegister;
    private final MachineUpdate machineUpdate;

    public MachineController(MachineQuery machineQuery,
                              MachineRegister machineRegister,
                              MachineUpdate machineUpdate) {
        this.machineQuery = machineQuery;
        this.machineRegister = machineRegister;
        this.machineUpdate = machineUpdate;
    }

    @GetMapping
    public List<Machine> getAll() {
        return machineQuery.findAll();
    }

    @GetMapping("/{machineId}")
    public Object getById(@PathVariable Integer machineId) {
        Machine machine = machineQuery.byId(machineId);
        if (machine == null) {
            return "Máquina con ID " + machineId + " no encontrada";
        }
        return machine;
    }

    @PostMapping
    public Object register(@RequestBody String json) {
        try {
            Machine machine = machineRegister.save(json);
            return machine;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/{machineId}/status/{statusId}")
    public Object updateStatus(@PathVariable Integer machineId, @PathVariable Integer statusId) {
        try {
            Machine machine = machineUpdate.updateStatus(machineId, statusId);
            return machine;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}