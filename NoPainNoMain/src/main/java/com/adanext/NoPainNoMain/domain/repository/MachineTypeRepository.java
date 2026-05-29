package com.adanext.NoPainNoMain.domain.repository;
import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.MachineType;

public interface MachineTypeRepository {
    MachineType save(MachineType machineType);
    Optional<MachineType> findById(Integer id);
    List<MachineType> findAll();
    void deleteById(Integer id);
    
}