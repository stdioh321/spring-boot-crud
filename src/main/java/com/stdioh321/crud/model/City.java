package com.stdioh321.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@SQLDelete(sql = "UPDATE City SET deleted_at=CURRENT_TIME WHERE id=?")
/*
@Where(clause = "deleted_at IS NULL")
*/
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

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_state", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

    private State state;


    public UUID getId_state() {
        return state.getId();
    }

    /*@JsonIgnore*/
    public State getState() {
        return state;
    }


    /*public void setState(State state) {
        this.state = state;
    }*/
}
