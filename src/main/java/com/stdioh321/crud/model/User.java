package com.stdioh321.crud.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BasicModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    @Pattern(regexp = "^\\D+")
    private String name;

    @NotBlank
    @NotNull
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\D+")
    private String username;

    @NotBlank
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @NotBlank
    @NotNull
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Role> roles;

    @JsonProperty(value = "roles")
    public Set<String> getRoleNames(){
        return roles.stream().map(role -> role.getName()).collect(Collectors.toSet());
    }

    public void setEmail(String email) {
        if (!Objects.isNull(email)) email.toLowerCase().trim();
        this.email = email;
    }

    public void setUsername(String username) {
        if (!Objects.isNull(username)) username.trim();
        this.username = username;
    }

    public void setName(String name) {
        if (!Objects.isNull(name)) name.trim();

        this.name = name;
    }
}
