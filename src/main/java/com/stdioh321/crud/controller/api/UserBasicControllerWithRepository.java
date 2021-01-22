package com.stdioh321.crud.controller.api;

import com.stdioh321.crud.model.User;
import com.stdioh321.crud.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("${api.url}/user")
public class UserBasicControllerWithRepository extends BasicControllerWithRepository<User, UUID> {

    @Autowired
    public UserBasicControllerWithRepository(UserRepository repository) {
        super(repository);
    }

    @Override
    public ResponseEntity post(@Valid User entity, BindingResult result) {
        return super.post(entity, result);
    }

    @Override
        public ResponseEntity<User> getById(UUID uuid) {
        return super.getById(uuid);
    }
}
