package com.adanext.NoPainNoMain.domain;

import java.time.LocalTime;

public class TimeSlot {

    private Integer id;
    private String name;
    private LocalTime startTime;

    public TimeSlot() {
    }

    public TimeSlot(Integer id, String name, LocalTime startTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public LocalTime getStartTime() { return startTime; }
}
