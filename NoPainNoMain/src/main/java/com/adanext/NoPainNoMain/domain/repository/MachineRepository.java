package com.adanext.NoPainNoMain.domain.repository;

import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.Machine;

public interface MachineRepository {
    Machine save(Machine machine);
    Optional<Machine> findById(Integer id);
    List<Machine> findAll();
    void deleteById(Integer id);
}
