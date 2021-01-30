package com.stdioh321.crud.repository;

import com.stdioh321.crud.model.User;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends IRepositoryExtender<User, String> {
    /*@Query("SELECT u FROM User u WHERE deleted_at <> NULL AND email=?1")
    User findByEmail(String email);*/

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND (u.username = ?1 OR u.email = ?1)")
    Optional<User> findByUsernameOrEmail(String username);
   /* default Optional<User> findByUsernameOrEmail(String username) {
        System.out.println(username);
        var tempUser = findAllActive().stream().filter(user -> {
            System.out.println(user);
            return user.getDeletedAt() == null && (user.getUsername().equals(username) || user.getEmail().equals(username));
        }).findFirst();
        return tempUser;
    }*/
}
