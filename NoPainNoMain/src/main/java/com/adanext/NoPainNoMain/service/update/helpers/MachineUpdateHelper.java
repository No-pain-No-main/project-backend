package com.adanext.NoPainNoMain.service.update.helpers;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import com.adanext.NoPainNoMain.domain.repository.MachineStatusRepository;
import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import org.springframework.stereotype.Component;

@Component
public class MachineUpdateHelper {

  private final MachineRepository machineRepository;
  private final MachineStatusRepository machineStatusRepository;

  public MachineUpdateHelper(
      MachineRepository machineRepository,
      MachineStatusRepository machineStatusRepository) {
    this.machineRepository = machineRepository;
    this.machineStatusRepository = machineStatusRepository;
  }

  public Machine findMachine(Integer machineId) {
    return machineRepository
        .findById(machineId)
        .orElseThrow(
            () -> new IllegalStateException("La máquina con ID " + machineId + " no existe"));
  }

  public MachineStatus findStatus(Integer statusId) {
    return machineStatusRepository
        .findById(statusId)
        .orElseThrow(
            () ->
                new IllegalStateException(
                    "El estado de máquina con ID " + statusId + " no existe"));
  }

  public Machine updateStatus(Machine machine, Integer statusId) {
    MachineStatus newStatus = findStatus(statusId);
    machine.updateStatus(newStatus);
    return machineRepository.save(machine);
  }
}
