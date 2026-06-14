package com.adanext.NoPainNoMain.config;

import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ReferenceDeserializer<T> extends JsonDeserializer<T> {

    private static final ObjectMapper PLAIN_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final Function<String, T> loadFunction; // ← String cubre Integer.toString() y strings puros
    private final Class<T> targetClass;

    public ReferenceDeserializer(Function<String, T> loadFunction, Class<T> targetClass) {
        this.loadFunction = loadFunction;
        this.targetClass = targetClass;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        // Caso 1: ID numérico directo  →  3
        if (node.isNumber()) {
            return loadFunction.apply(node.asText()); // asText() lo convierte a "3"
        }

        // Caso 2: ID como string  →  "3"  o  "CC"
        if (node.isTextual()) {
            return loadFunction.apply(node.asText());
        }

        // Caso 3: Objeto anidado  →  { "id": 3 }  o  { "name": "Nuevo", ... }
        if (node.isObject() && node.has("id") && !node.get("id").isNull()) {
            T existing = loadFunction.apply(node.get("id").asText());
            if (existing != null) return existing;
        }

        // Fallback: objeto nuevo sin ID o no encontrado en BD
        return PLAIN_MAPPER.treeToValue(node, targetClass);
    }
}