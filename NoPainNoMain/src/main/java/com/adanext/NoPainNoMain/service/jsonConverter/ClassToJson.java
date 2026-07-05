package com.adanext.NoPainNoMain.service.jsonconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ClassToJson {
  private final ObjectMapper objectMapper;

  public ClassToJson(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String convert(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error converting object to JSON", e);
    }
  }
}
