package com.adanext.NoPainNoMain.domain;

import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.domain.types.MachineType;


public class Machine {

    private Integer id; // Inmutable, nunca cambia
    private String name;
    private MachineType machineType;
    private MachineStatus machineStatus;

    public Machine() {
        // Constructor vacío para frameworks que lo requieran
    }
    // Constructor con los campos definitivos
    public Machine(Integer id, String name, MachineType machineType, MachineStatus machineStatus) {
        this.id = id;
        this.name = name;
        this.machineType = machineType;
        this.machineStatus = machineStatus;
    }

    // Getters
    public Integer getId() { return id; }
    public String getName() { return name; }
    public MachineType getMachineType() { return machineType; }
    public MachineStatus getMachineStatus() { return machineStatus; }

    // Setters para deserialización Jackson
    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMachineType(MachineType machineType) { this.machineType = machineType; }
    public void setMachineStatus(MachineStatus machineStatus) { this.machineStatus = machineStatus; }

    // Métodos específicos para que el Administrador modifique los datos
    public void updateDetails(String newName, MachineType newType) {
        this.name = newName;
        this.machineType = newType;
    }

    public void updateStatus(MachineStatus newStatus) {
        this.machineStatus = newStatus;
    }
}