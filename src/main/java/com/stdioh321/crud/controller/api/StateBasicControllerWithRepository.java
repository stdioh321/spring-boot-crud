package com.stdioh321.crud.controller.api;

import com.stdioh321.crud.model.State;
import com.stdioh321.crud.repository.StateRepository;
import com.stdioh321.crud.utils.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.url}/repository" + Routes.STATE)
public class StateBasicControllerWithRepository extends BasicControllerWithRepository<State, String> {

    @Autowired
    public StateBasicControllerWithRepository(StateRepository repository) {
        super(repository);
    }
}
