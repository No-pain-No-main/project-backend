package com.adanext.NoPainNoMain.domain;

import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.domain.types.MachineType;


public class Machine {

    private final Integer id; // Inmutable, nunca cambia
    private String name;
    private MachineType machineType;
    private MachineStatus machineStatus;

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

    // Métodos específicos para que el Administrador modifique los datos
    public void updateDetails(String newName, MachineType newType) {
        this.name = newName;
        this.machineType = newType;
    }

    public void updateStatus(MachineStatus newStatus) {
        this.machineStatus = newStatus;
    }
}