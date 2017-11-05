package com.lg.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;

public class Json implements SerializeJson {

    @Override
    public String serialize(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    @Override
    public Object deserialize(String json, Class<?> targetClass) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectReader r = mapper.readerFor(targetClass);

        return r.readValue(json);
    }
}
