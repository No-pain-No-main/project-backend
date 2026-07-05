package com.adanext.NoPainNoMain.domain.repository;

import com.adanext.NoPainNoMain.domain.TimeSlot;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository {
  TimeSlot save(TimeSlot timeSlot);

  Optional<TimeSlot> findById(Integer id);

  List<TimeSlot> findAll();

  void deleteById(Integer id);
}
