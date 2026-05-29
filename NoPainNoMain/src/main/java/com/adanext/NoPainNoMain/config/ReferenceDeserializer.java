package com.adanext.NoPainNoMain.config;
import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ReferenceDeserializer<T> extends JsonDeserializer<T>{
    private final Function<Integer, T> loadFunction;

    public ReferenceDeserializer(Function<Integer, T> loadFunction) {
        this.loadFunction = loadFunction;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        
        // Extrae el ID del objeto anidado (ej: { "id": 1 })
        if (node.has("id")) {
            int id = node.get("id").asInt();
            // Va a la base de datos y trae el objeto real poblado
            return loadFunction.apply(id); 
        }
        
        return null;
    }
    
}
