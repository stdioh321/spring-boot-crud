package com.stdioh321.crud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
/*@SQLDelete(sql = "UPDATE City SET deleted_at=CURRENT_TIME,updated_at=CURRENT_TIME WHERE id=?", callable = true)*/
/*
@Where(clause = "deleted_at IS NULL")
*/
public class City extends BasicModel {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotNull
    @Column(nullable = false, unique = true)
    @NotBlank
    @Pattern(regexp = "^\\D+$", message = "Should only contain letters")
    private String name;

    @OneToOne
    @JoinColumn(name = "id_state", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

    private State state;


    public String getId_state() {
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
