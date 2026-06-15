package com.adanext.NoPainNoMain.service.query;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import org.springframework.stereotype.Service;

@Service
public class MachineQuery {

  private final MachineRepository machineRepository;

  public MachineQuery(MachineRepository machineRepository) {
    this.machineRepository = machineRepository;
  }

  public Machine byId(Integer machineId) {
    return machineRepository.findById(machineId).orElse(null);
  }

  public Machine byName(String machineName) {
    return machineRepository.findByName(machineName).orElse(null);
  }
}
