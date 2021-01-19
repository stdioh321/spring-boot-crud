package com.stdioh321.crud.controller.api;

import com.stdioh321.crud.model.User;
import com.stdioh321.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.url}/user")
public class UserBasicControllerWithRepository extends BasicControllerWithRepository<User, UUID> {

    @Autowired
    public UserBasicControllerWithRepository(UserRepository repository) {
        super(repository);
    }
}
