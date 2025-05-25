package com.example.health_connection.serializers;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class InstantSerializer extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant instant, JsonGenerator generator, SerializerProvider provider) 
        throws IOException {
            if (instant != null) {
                generator.writeString(instant.toString());
            }
        }
    }
