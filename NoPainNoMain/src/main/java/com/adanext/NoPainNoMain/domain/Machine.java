package com.adanext.NoPainNoMain.domain;

import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.domain.types.MachineType;


public class Machine {

    private Integer id; 
    private String name;
    private MachineType machineType;
    private MachineStatus machineStatus;

    public Machine() {
    }
    public Machine(Integer id, String name, MachineType machineType, MachineStatus machineStatus) {
        this.id = id;
        this.name = name;
        this.machineType = machineType;
        this.machineStatus = machineStatus;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public MachineType getMachineType() { return machineType; }
    public MachineStatus getMachineStatus() { return machineStatus; }

    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMachineType(MachineType machineType) { this.machineType = machineType; }
    public void setMachineStatus(MachineStatus machineStatus) { this.machineStatus = machineStatus; }

    public void updateDetails(String newName, MachineType newType) {
        this.name = newName;
        this.machineType = newType;
    }

    public void updateStatus(MachineStatus newStatus) {
        this.machineStatus = newStatus;
    }
}