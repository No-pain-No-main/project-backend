package com.adanext.NoPainNoMain.domain.repository;

import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.Booking;

public interface BookingRepository {
    Booking save(Booking booking);
    Optional<Booking> findById(Integer id);
    List<Booking> findAll();
    void deleteById(Integer id);
    
    // Un método extra que seguro necesitarás para validar cruces de horarios
    List<Booking> findByMachineId(Integer machineId);
}