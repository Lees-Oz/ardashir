package com.lg.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface IJsonSerializer {
    String serialize(Object object) throws JsonProcessingException;
    Object deserialize(String json, Class<?> targetClass) throws IOException;
}
