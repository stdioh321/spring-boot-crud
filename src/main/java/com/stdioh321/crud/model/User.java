package com.stdioh321.crud.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user")
@Data

public class User extends BasicModel{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

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
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\!\\@\\#\\$\\%\\&\\=\\?])[a-zA-Z\\d\\!\\@\\#\\$\\%\\&\\=\\?]{8,}$",message = "Password should have Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character (!@#$%&=?)")
    private String password;

    @NotBlank
    @NotNull
    @Column(nullable = false, unique = true)
    @Email
    private String email;


    public void setEmail(String email) {
        if(!Objects.isNull(email)) email.toLowerCase().trim();
        this.email = email;
    }
}
