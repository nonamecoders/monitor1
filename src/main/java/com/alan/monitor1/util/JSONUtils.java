package com.alan.monitor1.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public final class JSONUtils {
    private final ObjectMapper mapper;
    
    private JSONUtils() {
        mapper = new ObjectMapper();
        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, true);
        mapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }
    
    public static JSONUtils getInstance() {
        return new JSONUtils();
    }
    
    private static ObjectMapper getMapper() {
        return getInstance().mapper;
    }
    
    public static String toJson(Object object) {
        try {
            return getMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T> T fromJson(String jsonStr, Class<T> cls) {
            try {
                return getMapper().readValue(jsonStr, cls);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
    
    public static <T> T fromJson(String jsonStr, TypeReference<T> typeReference) {
        try {
            return getMapper().readValue(jsonStr, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static JsonNode fromJson(String json) throws Exception {
        try {
            return getMapper().readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static <T extends Collection> T fromJson(String jsonStr, CollectionType collectionType) {
        try {
            return getMapper().readValue(jsonStr, collectionType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String toPrettyJson(String json) {
        Object jsonObject = JSONUtils.fromJson(json, Object.class);
        try {
            return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static Object JsonToObjWithoutRoot(String json, Class<?> targetClass, String rootName) {
        
        Object output = null;
        ObjectMapper mapper = new ObjectMapper();
        
        @SuppressWarnings("deprecation")
        ObjectReader reader = mapper.reader(targetClass).withRootName(rootName);
        
        try {
            output = reader.readValue(json);
        } catch (Exception e) {
            output = null;
        }
        
        return output;
    }

    /**
     * JSON 파일 읽기
     * @param filename
     * @return
     */
    public static String readFromFile(String filename) {
        String s = "";

        try {
            InputStream inputStream = new ClassPathResource("json/" + filename).getInputStream();

            s = new String(FileCopyUtils.copyToByteArray(inputStream), "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();

        }


        return s;
    }
    
}