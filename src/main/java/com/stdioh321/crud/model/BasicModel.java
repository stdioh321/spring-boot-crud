package com.stdioh321.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreRemove;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
@Data
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
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


    @PreRemove
    public void preRemove() {
        System.out.println(this);
    /*    var tmpDate = new Date();
        this.setUpdatedAt(tmpDate);
        this.setDeletedAt(tmpDate);*/
        System.out.println("preRemove ---------------------------------------");
    }


    public BasicModel updateOnlyNotNull(BasicModel entity, String[] fieldsToIgnore) {
        if (Objects.isNull(fieldsToIgnore)) fieldsToIgnore = new String[]{};
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
