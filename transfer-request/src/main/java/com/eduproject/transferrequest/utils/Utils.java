package com.eduproject.transferrequest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            String message = "Error during processing object " + obj.getClass().getName() + " into json-string";
            throw new RuntimeException(message, e);
        }
    }

    public static <T> T toObject(String json, Class<T> toCast) {
        try {
            return mapper.readValue(json, toCast);
        } catch (JsonProcessingException e) {
            String message = "Error during processing json-string " + toCast.getName() + " into object";
            throw new RuntimeException(message, e);
        }
    }
}
