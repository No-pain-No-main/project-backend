package com.adanext.NoPainNoMain.persistence;
import com.adanext.NoPainNoMain.persistence.types.MachineStatusEntity;
import com.adanext.NoPainNoMain.persistence.types.MachineTypeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Machine", schema = PersistenceConstants.SCHEMA)
public class MachineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name; // Ej: "TR-01" (Treadmill 1)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_type_id", nullable = false)
    private MachineTypeEntity machineType; // Muchos aparatos pertenecen a un Tipo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_status_id", nullable = false)
    private MachineStatusEntity machineStatus; // Muchos aparatos tienen un Estado

    // Requerido por JPA
    public MachineEntity() {}

    public MachineEntity(Integer id, String name, MachineTypeEntity machineType, MachineStatusEntity machineStatus) {
        this.id = id;
        this.name = name;
        this.machineType = machineType;
        this.machineStatus = machineStatus;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public MachineTypeEntity getMachineType() { return machineType; }
    public void setMachineType(MachineTypeEntity machineType) { this.machineType = machineType; }

    public MachineStatusEntity getMachineStatus() { return machineStatus; }
    public void setMachineStatus(MachineStatusEntity machineStatus) { this.machineStatus = machineStatus; }
}