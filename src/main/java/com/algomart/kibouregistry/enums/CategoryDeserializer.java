package com.algomart.kibouregistry.enums;

import com.algomart.kibouregistry.enums.Category;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class CategoryDeserializer extends JsonDeserializer<Category> {
    @Override
    public Category deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Category.valueOf(p.getText().toUpperCase()); // Ensures case-insensitive handling
    }
}
