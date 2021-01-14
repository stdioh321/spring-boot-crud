package com.stdioh321.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Entity
@Data
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "state")
@SQLDelete(sql = "UPDATE State SET deleted_at=CURRENT_TIME WHERE id=?")
/*
@Where(clause = "deleted_at IS NULL")
*/

public class State extends BasicModel{
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

    @NotNull
    @Column(nullable = false, unique = true)
    @NotBlank
    @Pattern(regexp = "^\\D+$", message = "Should only contain letters")
    @Length(max = 2, min = 2,message = "Max/Min is 2 letters")
    private String initial;

}
