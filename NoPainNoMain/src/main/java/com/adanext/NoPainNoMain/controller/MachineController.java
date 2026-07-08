package com.adanext.NoPainNoMain.controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final ObjectMapper objectMapper;

    public MachineController(MachineQuery machineQuery,
                              MachineRegister machineRegister,
                              MachineUpdate machineUpdate,
                              ObjectMapper objectMapper) {
        this.machineQuery = machineQuery;
        this.machineRegister = machineRegister;
        this.machineUpdate = machineUpdate;
        this.objectMapper = objectMapper;
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
    public Object register(@RequestBody Map<String, Object> body) {
        try {
            String json = objectMapper.writeValueAsString(body);
            Machine machine = machineRegister.save(json);
            return machine;
        } catch (IllegalStateException | JsonProcessingException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/{machineId}")
    public Object updateMachine(@PathVariable Integer machineId, @RequestBody Map<String, Object> body) {
        try {
            String name = (String) body.get("name");
            Integer typeId = body.get("typeId") != null ? ((Number) body.get("typeId")).intValue() : null;
            Integer statusId = body.get("statusId") != null ? ((Number) body.get("statusId")).intValue() : null;
            Machine machine = machineUpdate.updateMachine(machineId, name, typeId, statusId);
            return machine;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/{machineId}")
    public Object deleteMachine(@PathVariable Integer machineId) {
        try {
            machineUpdate.deleteMachine(machineId);
            return "Máquina con ID " + machineId + " eliminada correctamente";
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
