package com.stdioh321.crud.utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface IRepositoryExtender<T, U> extends JpaRepository<T, U> {

    default public List findByQuery(String q, List<?> all) {
        if (Objects.isNull(all)) {
            all = findAll();
        }
        q = Objects.isNull(q) ? "" : q.trim().toLowerCase();
        String[] qItems = q.split(",");
        String qStr = qItems[0];
        String[] qFields = qItems.length > 0 ? Arrays.copyOfRange(qItems, 1, qItems.length) : new String[]{};

        if (q.isEmpty()) return all;

        all = all.stream().filter(obj -> {

            var fields = Stream.of(obj.getClass().getSuperclass().getDeclaredFields(), obj.getClass().getDeclaredFields())
                    .flatMap(Stream::of).toArray(Field[]::new);


            return Arrays.stream(fields).anyMatch(field -> {
                field.setAccessible(true);

                boolean hasMatch = false;
                try {
                    if (qFields.length > 0) {
                        if (Arrays.stream(qFields).anyMatch(s -> {
                            return s.equals(field.getName().toLowerCase().trim());
                        })) {

                            hasMatch = field.get(obj).toString().toLowerCase().trim().contains(qStr);
                        }
                    } else {
                        hasMatch = field.get(obj).toString().toLowerCase().trim().contains(qStr);
                    }
                } catch (Exception e) {
                    /*e.printStackTrace();*/
                    System.out.println(e);
                }


                return hasMatch;
            });
        }).collect(Collectors.toList());

        return all;


    }

    default public List findByFields(String fields, List<?> all) {
        if (Objects.isNull(all)) {
            all = findAll();
        }
        fields = Objects.isNull(fields) ? "" : fields.toLowerCase().trim();
        if (fields.isEmpty()) return all;


        if (!fields.isEmpty()) {
            List<Map<String, Object>> tmpAll = new ArrayList<>();
            String[] fieldItems = fields.split(",");
            tmpAll = all.stream().map(o -> {
                Map<String, Object> currItem = new HashMap<>();
                for (Field f : o.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    boolean isPresent = Arrays.stream(fieldItems).anyMatch(s -> {
                        return s.equals(f.getName().toLowerCase());
                    });
                    if (isPresent) {
                        try {
                            currItem.put(f.getName(), f.get(o));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return currItem;
            }).collect(Collectors.toList());

            all = tmpAll;
        }
        return all;
    }

    default public List findByFieldQuery(String fields, String q, List<?> all) {
        if (Objects.isNull(all)) {
            all = findAll();
        }
        fields = Objects.isNull(fields) ? "" : fields.toLowerCase().trim();
        q = Objects.isNull(q) ? "" : q.toLowerCase().trim();
        if (fields.isEmpty() && q.isEmpty()) return all;

        all = findByQuery(q, all);
        all = findByFields(fields, all);

        return all;
    }
}