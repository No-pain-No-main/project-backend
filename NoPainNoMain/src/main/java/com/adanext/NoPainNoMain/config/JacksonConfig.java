package com.adanext.NoPainNoMain.config;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.domain.repository.AdministratorRepository;
import com.adanext.NoPainNoMain.domain.repository.BookingRepository;
import com.adanext.NoPainNoMain.domain.repository.BookingStatusRepository;
import com.adanext.NoPainNoMain.domain.repository.DocumentTypeRepository;
import com.adanext.NoPainNoMain.domain.repository.GenderRepository;
import com.adanext.NoPainNoMain.domain.repository.MachineRepository;
import com.adanext.NoPainNoMain.domain.repository.MachineStatusRepository;
import com.adanext.NoPainNoMain.domain.repository.MachineTypeRepository;
import com.adanext.NoPainNoMain.domain.repository.StudentRepository;
import com.adanext.NoPainNoMain.domain.repository.TimeSlotRepository;
import com.adanext.NoPainNoMain.domain.repository.UserStatusRepository;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.domain.types.MachineType;
import com.adanext.NoPainNoMain.domain.types.UserStatus;
import com.adanext.NoPainNoMain.persistence.impl.AdministratorRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.BookingStatusRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.DocumentTypeRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.GenderRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.MachineRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.MachineStatusRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.MachineTypeRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.StudentRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.TimeSlotRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.UserStatusRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(
            DocumentTypeRepositoryImpl docRepository,
            GenderRepositoryImpl genderRepository,
            UserStatusRepositoryImpl statusRepository,
            MachineTypeRepositoryImpl machineTypeRepository,
            MachineStatusRepositoryImpl machineStatusRepository,
            BookingStatusRepositoryImpl bookingStatusRepository,
            StudentRepositoryImpl studentRepository,
            AdministratorRepositoryImpl administratorRepository,
            MachineRepositoryImpl machineRepository,
            TimeSlotRepositoryImpl timeSlotRepository) {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        
        SimpleModule catalogoModule = new SimpleModule();

    catalogoModule.addDeserializer(
        DocumentType.class,
        new ReferenceDeserializer<>(
            id -> docRepository.findById(Integer.parseInt(id)).orElse(null), DocumentType.class));

    catalogoModule.addDeserializer(
        Gender.class,
        new ReferenceDeserializer<>(
            id -> genderRepository.findById(Integer.parseInt(id)).orElse(null), Gender.class));

    catalogoModule.addDeserializer(
        UserStatus.class,
        new ReferenceDeserializer<>(
            id -> statusRepository.findById(Integer.parseInt(id)).orElse(null), UserStatus.class));

    catalogoModule.addDeserializer(
        Student.class,
        new ReferenceDeserializer<>(
            id -> studentRepository.findByDocumentNumber(id).orElse(null), Student.class));
    catalogoModule.addDeserializer(
        Administrator.class,
        new ReferenceDeserializer<>(
            id -> administratorRepository.findByDocumentNumber(id).orElse(null), Administrator.class));
    catalogoModule.addDeserializer(
        Machine.class,
        new ReferenceDeserializer<>(
            id -> machineRepository.findById(Integer.parseInt(id)).orElse(null), Machine.class));
    catalogoModule.addDeserializer(
        MachineType.class,
        new ReferenceDeserializer<>(
            id -> machineTypeRepository.findById(Integer.parseInt(id)).orElse(null),
            MachineType.class));
    catalogoModule.addDeserializer(
        MachineStatus.class,
        new ReferenceDeserializer<>(
            id -> machineStatusRepository.findById(Integer.parseInt(id)).orElse(null),
            MachineStatus.class
        ));
        catalogoModule.addDeserializer(BookingStatus.class, new ReferenceDeserializer<>(
            id -> bookingStatusRepository.findById(Integer.parseInt(id)).orElse(null),
            BookingStatus.class));
    catalogoModule.addDeserializer(
        TimeSlot.class,
        new ReferenceDeserializer<>(
            id -> timeSlotRepository.findById(Integer.parseInt(id)).orElse(null), TimeSlot.class));

    mapper.registerModule(catalogoModule);

    return mapper;
  }
}
