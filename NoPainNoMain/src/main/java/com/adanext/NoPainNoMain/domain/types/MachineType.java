package com.adanext.NoPainNoMain.domain.types;

public class MachineType {
    private final Integer id;
    private final String name;

    public MachineType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
}
