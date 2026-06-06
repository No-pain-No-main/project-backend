package com.adanext.NoPainNoMain.domain.types;

public class BookingStatus {
    private  Integer id;
    private  String name;

   

    public BookingStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public BookingStatus() {}

    public Integer getId() { return id; }
    public String getName() { return name; }
}