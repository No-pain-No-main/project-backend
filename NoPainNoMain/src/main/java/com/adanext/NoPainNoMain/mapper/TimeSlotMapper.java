package com.adanext.NoPainNoMain.mapper;

import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.persistence.entities.TimeSlotEntity;

public class TimeSlotMapper {

  public static TimeSlot toDomain(TimeSlotEntity entity) {
    if (entity == null) {
      return null;
    }

    return new TimeSlot(entity.getId(), entity.getName(), entity.getStartTime());
  }

  public static TimeSlotEntity toEntity(TimeSlot domain) {
    if (domain == null) {
      return null;
    }

    TimeSlotEntity entity = new TimeSlotEntity();
    entity.setId(domain.getId());
    entity.setName(domain.getName());
    entity.setStartTime(domain.getStartTime());

    return entity;
  }
}
