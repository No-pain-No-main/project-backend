package com.adanext.NoPainNoMain.domain.repository;

import com.adanext.NoPainNoMain.domain.types.MachineType;
import java.util.List;
import java.util.Optional;

public interface MachineTypeRepository {
  MachineType save(MachineType machineType);

  Optional<MachineType> findById(Integer id);

  List<MachineType> findAll();

  void deleteById(Integer id);
}
