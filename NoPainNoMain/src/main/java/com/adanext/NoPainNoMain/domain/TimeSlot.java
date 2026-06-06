package com.adanext.NoPainNoMain.domain;

public class TimeSlot {

    private Integer id;
    private String name;

    public TimeSlot() {
        // Constructor vacío para frameworks que lo requieran
    }

    public TimeSlot(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
