package com.stdioh321.crud.utils;

import com.stdioh321.crud.model.BasicModel;

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


}
