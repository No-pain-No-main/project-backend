package com.adanext.NoPainNoMain.service.register.helpers;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import org.springframework.stereotype.Component;

@Component
public class MachineRegisterHelper {

  private final MachineRepository repository;

  public MachineRegisterHelper(MachineRepository repository) {
    this.repository = repository;
  }

  public boolean isDuplicateName(Machine machine) {
    return machine.getName() != null && repository.findByName(machine.getName()).isPresent();
  }
}
