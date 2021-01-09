package com.stdioh321.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
@Data
public abstract class BasicModel {

    @CreatedDate
    @Column(name = "created_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

    @Column(name = "deleted_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date deletedAt;

    public BasicModel updateOnlyNotNull(BasicModel entity, String[] fieldsToIgnore) {
        if (!getClass().equals(entity.getClass())) {
            return null;
        }
        try {
            for (Field f : getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (Arrays.stream(fieldsToIgnore).anyMatch(s -> f.getName().equals(s))) break;
                /*if(Arrays.stream(f.getAnnotations()).anyMatch(annotation -> annotation.getClass().equals(LastModifiedDate.class))) break;*/

                if (!Objects.isNull(f.get(entity))) {
                    f.set(this, f.get(entity));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return entity;
    }
}
