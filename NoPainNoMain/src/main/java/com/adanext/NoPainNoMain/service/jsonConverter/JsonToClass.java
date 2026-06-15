package com.adanext.NoPainNoMain.service.jsonconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonToClass<T> {
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
