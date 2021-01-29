package com.stdioh321.crud.utils;

import com.stdioh321.crud.model.BasicModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface IRepositoryExtender<ENTITY extends BasicModel, ID> extends PagingAndSortingRepository<ENTITY, ID> {

    default Class<?> getEntityClass() {
        return (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Query("SELECT e FROM #{#entityName} e")
    List<ENTITY> findAll();

    @Query("SELECT e FROM #{#entityName} e WHERE deleted_at IS NULL")
    List<ENTITY> findAllActive();


    @Query("SELECT e FROM #{#entityName} e WHERE id = ?1 AND  deleted_at IS NULL ")
    Optional<ENTITY> findByIdActive(ID ID);

    @Query("SELECT e FROM #{#entityName} e WHERE deleted_at <> NULL")
    List<ENTITY> findTrashed();

    @Query("SELECT e FROM #{#entityName} e WHERE id = ?1 AND  deleted_at <> NULL ")
    Optional<ENTITY> findTrashedById(ID ID);


    default public Page<ENTITY> findPaginateActive(PageRequest pageRequest) {
        return findAll(pageRequest);
    }

    default public Optional<ENTITY> deleteSoft(ID id) {
        var opt = findByIdActive(id);
        if (opt.isEmpty()) return opt;
        var currentEntity = opt.get();
        currentEntity.setDeletedAt(new Date());
        currentEntity = save(currentEntity);
        return Optional.of(currentEntity);
    }

    @Query("UPDATE #{#entityName} e SET deleted_at = NULL WHERE e.id = :id")
    @Modifying
    default Optional<ENTITY> restore(@Param("id") ID ID) {
        var opt = findTrashedById(ID);
        if (opt.isEmpty()) return opt;
        var ent = opt.get();

        ent.setDeletedAt(null);
        ent = save(ent);
        return Optional.of(ent);
    }


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