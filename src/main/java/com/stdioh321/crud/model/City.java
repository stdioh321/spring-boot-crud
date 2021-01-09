package com.stdioh321.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Entity
@Table(name = "city")
@DynamicUpdate
@Data
@EntityListeners(AuditingEntityListener.class)
public class City extends BasicModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull
    @Column(nullable = false, unique = true)
    @NotBlank
    @Pattern(regexp = "^\\D+$", message = "Should only contain letters")
    private String name;
}
