package com.adanext.NoPainNoMain.domain.repository;
import java.util.List;
import java.util.Optional;

import com.adanext.NoPainNoMain.domain.types.MachineStatus;

public interface MachineStatusRepository {
    MachineStatus save(MachineStatus machineStatus);
    Optional<MachineStatus> findById(Integer id);
    List<MachineStatus> findAll();
    void deleteById(Integer id);
    
}