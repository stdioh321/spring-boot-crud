package com.stdioh321.crud.controller.api;

import com.stdioh321.crud.model.State;
import com.stdioh321.crud.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.url}/state")
public class StateBasicControllerWithService extends BasicControllerWithService<State, UUID> {

    @Autowired
    public StateBasicControllerWithService(StateService service) {
        super(service);

    }
}
