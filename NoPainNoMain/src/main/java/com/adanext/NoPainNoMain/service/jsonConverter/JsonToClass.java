package com.adanext.NoPainNoMain.service.jsonConverter;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonToClass<T>{
    private final ObjectMapper objectMapper;

    public JsonToClass(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
     
    public T convert(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new RuntimeException("Error converting JSON to " + clazz.getSimpleName(), e);
        }
    }
}
