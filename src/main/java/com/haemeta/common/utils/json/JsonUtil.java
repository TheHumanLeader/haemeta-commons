package com.haemeta.common.utils.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haemeta.common.entity.lang.JsonMap;

import java.io.InputStream;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static <T> String toString(T t){
        try {
            return mapper.writeValueAsString(t);
        }catch (Exception exception){
            exception.printStackTrace();
            return "{}";
        }
    }

    public static <T> T parse(String text,Class<T> tClass){
        try {
            return mapper.readValue(text,tClass);
        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static <T> T parse(InputStream io, Class<T> tClass){
        try {
            return mapper.readValue(io,tClass);
        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static JsonMap parse(String text){
        try {
            return mapper.readValue(text,JsonMap.class);
        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }


}
