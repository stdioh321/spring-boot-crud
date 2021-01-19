package com.stdioh321.crud.service;


import com.stdioh321.crud.model.User;
import com.stdioh321.crud.repository.UserRepository;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends GenericService<User, UUID> {

    /*@Autowired
    UserRepository repository;*/

    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }

   /* public User getByEmail(String email) {
        return ((UserRepository) this.repository).findByEmail(email);
    }*/
}
