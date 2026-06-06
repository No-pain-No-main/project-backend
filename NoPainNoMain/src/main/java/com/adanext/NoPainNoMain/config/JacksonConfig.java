package com.adanext.NoPainNoMain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adanext.NoPainNoMain.domain.Administrator;
import com.adanext.NoPainNoMain.domain.Booking;
import com.adanext.NoPainNoMain.domain.Machine;
import com.adanext.NoPainNoMain.domain.Student;
import com.adanext.NoPainNoMain.domain.TimeSlot;
import com.adanext.NoPainNoMain.domain.types.BookingStatus;
import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.domain.types.MachineStatus;
import com.adanext.NoPainNoMain.domain.types.MachineType;
import com.adanext.NoPainNoMain.domain.types.UserStatus;
import com.adanext.NoPainNoMain.persistence.impl.AdministratorRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.BookingRepositoryImpl;
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
            BookingRepositoryImpl bookingRepository,
            TimeSlotRepositoryImpl timeSlotRepository) {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        
        SimpleModule catalogoModule = new SimpleModule();

        // Usamos una expresión lambda para desempaquetar el Optional de tus repositorios
        catalogoModule.addDeserializer(DocumentType.class, new ReferenceDeserializer<>(
            id -> docRepository.findById(Integer.parseInt(id)).orElse(null),
            DocumentType.class
        ));
        
        catalogoModule.addDeserializer(Gender.class, new ReferenceDeserializer<>(
            id -> genderRepository.findById(Integer.parseInt(id)).orElse(null),
            Gender.class
        ));
        
        catalogoModule.addDeserializer(UserStatus.class, new ReferenceDeserializer<>(
            id -> statusRepository.findById(Integer.parseInt(id)).orElse(null),
            UserStatus.class
        ));

        catalogoModule.addDeserializer(Student.class, new ReferenceDeserializer<>(
            id -> studentRepository.findByDocumentNumber(id).orElse(null),
            Student.class
        ));
        catalogoModule.addDeserializer(Administrator.class, new ReferenceDeserializer<>(
            id -> administratorRepository.findByDocumentNumber(id).orElse(null),
            Administrator.class
        )); 
        catalogoModule.addDeserializer(Machine.class, new ReferenceDeserializer<>(
            id -> machineRepository.findById(Integer.parseInt(id)).orElse(null),
            Machine.class
        ));
        catalogoModule.addDeserializer(MachineType.class, new ReferenceDeserializer<>(
            id -> machineTypeRepository.findById(Integer.parseInt(id)).orElse(null),
            MachineType.class
        ));
        catalogoModule.addDeserializer(MachineStatus.class, new ReferenceDeserializer<>(
            id -> machineStatusRepository.findById(Integer.parseInt(id)).orElse(null),
            MachineStatus.class
        ));
        catalogoModule.addDeserializer(Booking.class, new ReferenceDeserializer<>(
            id -> bookingRepository.findById(id).orElse(null),
            Booking.class
        ));
        catalogoModule.addDeserializer(BookingStatus.class, new ReferenceDeserializer<>(
            id -> bookingStatusRepository.findById(Integer.parseInt(id)).orElse(null),
            BookingStatus.class
        ));
        catalogoModule.addDeserializer(TimeSlot.class, new ReferenceDeserializer<>(
            id -> timeSlotRepository.findById(Integer.parseInt(id)).orElse(null),
            TimeSlot.class
        ));


        mapper.registerModule(catalogoModule);
        
        return mapper;
    }
}