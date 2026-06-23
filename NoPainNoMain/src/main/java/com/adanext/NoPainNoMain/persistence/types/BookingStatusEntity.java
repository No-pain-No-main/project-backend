package com.adanext.NoPainNoMain.persistence.types;

import com.adanext.NoPainNoMain.persistence.PersistenceConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BookingStatus", schema = PersistenceConstants.SCHEMA)
public class BookingStatusEntity {

    @Id
    
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    public BookingStatusEntity() {}

    public BookingStatusEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}