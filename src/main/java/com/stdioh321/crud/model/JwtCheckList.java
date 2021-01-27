package com.stdioh321.crud.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "jwt_check_list")
@DynamicUpdate
@Data
@EntityListeners(AuditingEntityListener.class)
public class JwtCheckList extends BasicModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    @NotNull
    @Column(nullable = false,length = 1000)
    private String token;

    @Column(nullable = false)
    private Boolean blacklist=true;
}
