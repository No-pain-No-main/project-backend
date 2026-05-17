package com.adanext.NoPainNoMain.domain;

import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.domain.types.MachineType;


public class Machine {

    private Integer id;
    private String name;
    private MachineType machineType;
    private MachineStatus machineStatus;
    private Integer maxUsageTime;
    private Integer weeklyBookingLimit;

    public Machine() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public MachineType getMachineType() { return machineType; }
    public void setMachineType(MachineType machineType) { this.machineType = machineType; }

    public MachineStatus getMachineStatus() { return machineStatus; }
    public void setMachineStatus(MachineStatus machineStatus) { this.machineStatus = machineStatus; }

    public Integer getMaxUsageTime() { return maxUsageTime; }
    public void setMaxUsageTime(Integer maxUsageTime) { this.maxUsageTime = maxUsageTime; }

    public Integer getWeeklyBookingLimit() { return weeklyBookingLimit; }
    public void setWeeklyBookingLimit(Integer weeklyBookingLimit) { this.weeklyBookingLimit = weeklyBookingLimit; }
}