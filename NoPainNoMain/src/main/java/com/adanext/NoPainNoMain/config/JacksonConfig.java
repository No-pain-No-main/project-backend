package com.adanext.NoPainNoMain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adanext.NoPainNoMain.domain.types.DocumentType;
import com.adanext.NoPainNoMain.domain.types.Gender;
import com.adanext.NoPainNoMain.domain.types.UserStatus;
import com.adanext.NoPainNoMain.persistence.impl.DocumentTypeRepositoryImpl;
import com.adanext.NoPainNoMain.persistence.impl.GenderRepositoryImpl;
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
            UserStatusRepositoryImpl statusRepository) {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        
        SimpleModule catalogoModule = new SimpleModule();

        // Usamos una expresión lambda para desempaquetar el Optional de tus repositorios
        catalogoModule.addDeserializer(DocumentType.class, new ReferenceDeserializer<>(
            id -> docRepository.findById(id).orElse(null)
        ));
        
        catalogoModule.addDeserializer(Gender.class, new ReferenceDeserializer<>(
            id -> genderRepository.findById(id).orElse(null)
        ));
        
        catalogoModule.addDeserializer(UserStatus.class, new ReferenceDeserializer<>(
            id -> statusRepository.findById(id).orElse(null)
        ));

        mapper.registerModule(catalogoModule);
        
        return mapper;
    }
}