package com.stdioh321.crud.repository;

import com.stdioh321.crud.model.User;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends IRepositoryExtender<User, UUID> {
    /*@Query("SELECT u FROM User u WHERE created_at <> NULL AND email=?1")
    User findByEmail(String email);*/
}
