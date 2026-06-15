package com.adanext.NoPainNoMain.mapper;

import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.mapper.types.MachineStatusMapper;
import com.adanext.NoPainNoMain.mapper.types.MachineTypeMapper;
import com.adanext.NoPainNoMain.persistence.entities.MachineEntity;

public class MachineMapper {

  public static Machine toDomain(MachineEntity entity) {
    if (entity == null) {
      return null;
    }

    return new Machine(
        entity.getId(),
        entity.getName(),
        MachineTypeMapper.toDomain(entity.getMachineType()),
        MachineStatusMapper.toDomain(entity.getMachineStatus()));
  }

  public static MachineEntity toEntity(Machine domain) {
    if (domain == null) {
      return null;
    }

    MachineEntity entity = new MachineEntity();
    entity.setId(domain.getId());
    entity.setName(domain.getName());
    entity.setMachineType(MachineTypeMapper.toEntity(domain.getMachineType()));
    entity.setMachineStatus(MachineStatusMapper.toEntity(domain.getMachineStatus()));

    return entity;
  }
}
