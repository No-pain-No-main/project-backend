package com.adanext.NoPainNoMain.domain.repository;

import com.adanext.NoPainNoMain.domain.Machine;
import java.util.List;
import java.util.Optional;

public interface MachineRepository {
  Machine save(Machine machine);

  Optional<Machine> findById(Integer id);

  List<Machine> findAll();

  void deleteById(Integer id);

  Optional<Machine> findByName(String name);
}
