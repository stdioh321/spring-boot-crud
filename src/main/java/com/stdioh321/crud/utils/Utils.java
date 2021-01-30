package com.stdioh321.crud.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.stdioh321.crud.model.AuthRequest;
import com.stdioh321.crud.model.AuthResponse;
import com.stdioh321.crud.model.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class Utils {


    public static <T> T mergeObjects(T first, T second) throws IllegalAccessException {
        Field[] fields = second.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            var value1 = f.get(second);
            if (f.get(second) != null) {
                f.set(first, f.get(second));
            }
        }
        return first;

    }

    public static String getRandomString(int n) {
        n = Objects.isNull(n) || n < 1 ? 3 : n;

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();

    }



    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public static String mapToGson(Object obj) {
        Gson g = new Gson();
        return g.toJson(obj);
    }

    public static <T> T mapFromGson(String json, Class<T> clazz) {
        Gson g = new Gson();

        return g.fromJson(json, clazz);
    }


}
