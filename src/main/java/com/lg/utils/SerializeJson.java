package com.lg.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface SerializeJson {
    String serialize(Object object) throws JsonProcessingException;
    Object deserialize(String json, Class<?> targetClass) throws IOException;
    Object deserialize(byte[] json, Class<?> targetClass) throws IOException;
    <T> T deserialize(String json) throws IOException;
}
