package com.adanext.NoPainNoMain.service.register;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import com.adanext.NoPainNoMain.service.jsonconverter.JsonToClass;
import com.adanext.NoPainNoMain.service.register.helpers.MachineRegisterHelper;
import org.springframework.stereotype.Service;

@Service
public class MachineRegister {

  private final JsonToClass<Machine> jsonToClass;
  private final MachineRepository machineRepository;
  private final MachineRegisterHelper helper;

  public MachineRegister(
      JsonToClass<Machine> jsonToClass,
      MachineRepository machineRepository,
      MachineRegisterHelper helper) {
    this.jsonToClass = jsonToClass;
    this.machineRepository = machineRepository;
    this.helper = helper;
  }

  public Machine save(String jsonRegister) {
    Machine machine = jsonToClass.convert(jsonRegister, Machine.class);

    if (helper.isDuplicateName(machine)) {
      throw new IllegalStateException(
          "La máquina '" + machine.getName() + "' ya existe en el sistema");
    }

    return machineRepository.save(machine);
  }
}
