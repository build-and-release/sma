package com.technovate.school_management.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class SafeGenericDeserializer<T> extends JsonDeserializer<T> implements ContextualDeserializer {

    private Class<T> targetType;

    // Default constructor
    public SafeGenericDeserializer() {
        // No-arg constructor required
    }

    // Constructor with targetType
    @SuppressWarnings("unchecked")
    public SafeGenericDeserializer(Class<?> targetType) {
        this.targetType = (Class<T>) targetType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        if (targetType == null) {
            // If targetType is still null, default to Object
            targetType = (Class<T>) Object.class;
        }

        try {
            if (node.isValueNode()) {
                // Handle different types
                if (targetType == String.class && node.isTextual()) {
                    return (T) node.asText();
                } else if (targetType == Integer.class && node.isInt()) {
                    return (T) Integer.valueOf(node.asInt());
                } else if (targetType == Long.class && node.isLong()) {
                    return (T) Long.valueOf(node.asLong());
                } else if (targetType == Double.class && node.isDouble()) {
                    return (T) Double.valueOf(node.asDouble());
                } else if (targetType == Boolean.class && node.isBoolean()) {
                    return (T) Boolean.valueOf(node.asBoolean());
                } else if (targetType == LocalDate.class && node.isTextual()) {
                    return (T) LocalDate.parse(node.asText());
                } else {
                    // For other types, attempt to deserialize using ObjectMapper
                    ObjectMapper mapper = (ObjectMapper) p.getCodec();
                    return mapper.treeToValue(node, targetType);
                }
            } else {
                // For non-value nodes (objects, arrays), attempt to deserialize using ObjectMapper
                ObjectMapper mapper = (ObjectMapper) p.getCodec();
                return mapper.treeToValue(node, targetType);
            }
        } catch (Exception e) {
            // Return null on type incompatibility or parsing error
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        JavaType type = null;

        if (property != null) {
            type = property.getType();
        }

        if (type == null) {
            type = ctxt.getContextualType();
        }

        if (type != null) {
            return new SafeGenericDeserializer<>(type.getRawClass());
        } else {
            // If type information is not available, default to Object
            return new SafeGenericDeserializer<>(Object.class);
        }
    }
}
