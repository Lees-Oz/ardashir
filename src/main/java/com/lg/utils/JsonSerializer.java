package com.lg.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;

public class JsonSerializer implements SerializeJson {
    final private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String serialize(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    @Override
    public Object deserialize(String json, Class<?> targetClass) throws IOException {
        ObjectReader r = mapper.readerFor(targetClass);
        return r.readValue(json);
    }

    @Override
    public Object deserialize(byte[] json, Class<?> targetClass) throws IOException {
        ObjectReader r = mapper.readerFor(targetClass);
        return r.readValue(new String(json));
    }

    @Override
    public <T> T deserialize(String json) throws IOException {
        return mapper.readValue(json, new TypeReference<T>() {});
    }
}
