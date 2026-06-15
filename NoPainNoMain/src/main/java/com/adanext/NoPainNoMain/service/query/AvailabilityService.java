package com.adanext.NoPainNoMain.service.query;

import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.TimeSlotRepositoryImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {

  private final BookingRepositoryImpl bookingRepository;
  private final MachineRepositoryImpl machineRepository;
  private final TimeSlotRepositoryImpl timeSlotRepository;

  public AvailabilityService(
      BookingRepositoryImpl bookingRepository,
      MachineRepositoryImpl machineRepository,
      TimeSlotRepositoryImpl timeSlotRepository) {
    this.bookingRepository = bookingRepository;
    this.machineRepository = machineRepository;
    this.timeSlotRepository = timeSlotRepository;
  }

  public List<TimeSlot> findFreeSlotsByMachine(Integer machineId, LocalDate date) {
    // Obtener TODAS las franjas horarias disponibles (10 slots: 7am-5pm)
    List<TimeSlot> allSlots = timeSlotRepository.findAll();

    // Obtener reservas para esa máquina en ese día
    List<Booking> bookings = bookingRepository.findByMachineIdAndDateBetween(machineId, date, date);

    // IDs de franjas ocupadas
    Set<Integer> bookedSlotIds =
        bookings.stream().map(b -> b.getTimeSlot().getId()).collect(Collectors.toSet());

    // Franjas NO ocupadas
    return allSlots.stream()
        .filter(slot -> !bookedSlotIds.contains(slot.getId()))
        .collect(Collectors.toList());
  }

  public List<MachineAvailability> findFreeSlotsForAllMachines(LocalDate date) {
    List<Machine> allMachines = machineRepository.findAll();

    return allMachines.stream()
        .map(
            machine -> {
              List<TimeSlot> freeSlots = findFreeSlotsByMachine(machine.getId(), date);
              return new MachineAvailability(machine, freeSlots);
            })
        .collect(Collectors.toList());
  }

  public static class MachineAvailability {
    private final Machine machine;
    private final List<TimeSlot> freeSlots;

    public MachineAvailability(Machine machine, List<TimeSlot> freeSlots) {
      this.machine = machine;
      this.freeSlots = freeSlots;
    }

    public Machine getMachine() {
      return machine;
    }

    public List<TimeSlot> getFreeSlots() {
      return freeSlots;
    }
  }
}
