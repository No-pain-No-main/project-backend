package com.adanext.NoPainNoMain.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.function.Function;

public class ReferenceDeserializer<T> extends JsonDeserializer<T> {

  private static final ObjectMapper PLAIN_MAPPER =
      new ObjectMapper().registerModule(new JavaTimeModule());

  private final Function<String, T>
      loadFunction; 
  private final Class<T> targetClass;

  public ReferenceDeserializer(Function<String, T> loadFunction, Class<T> targetClass) {
    this.loadFunction = loadFunction;
    this.targetClass = targetClass;
  }

  @Override
  public T deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    if (node.isNumber()) {
      return loadFunction.apply(node.asText()); 
    }

    // Caso 2: ID como string  →  "3"  o  "CC"
    if (node.isTextual()) {
      return loadFunction.apply(node.asText());
    }

    boolean isObjectWithId = node.isObject() && node.has("id") && !node.get("id").isNull();

    if (isObjectWithId) {
      T existing = loadFunction.apply(node.get("id").asText());
      if (existing != null) {
        return existing;
      }
    }

    return PLAIN_MAPPER.treeToValue(node, targetClass);
  }
}
