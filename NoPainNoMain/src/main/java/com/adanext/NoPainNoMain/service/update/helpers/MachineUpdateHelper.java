package com.adanext.NoPainNoMain.service.update.helpers;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.MachineStatusRepositoryImpl;
import org.springframework.stereotype.Component;

@Component
public class MachineUpdateHelper {

  private final MachineRepositoryImpl machineRepository;
  private final MachineStatusRepositoryImpl machineStatusRepository;

  public MachineUpdateHelper(
      MachineRepositoryImpl machineRepository,
      MachineStatusRepositoryImpl machineStatusRepository) {
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
