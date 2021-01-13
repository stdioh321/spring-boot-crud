package com.stdioh321.crud.controller;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.model.State;
import com.stdioh321.crud.repository.CityRepository;
import com.stdioh321.crud.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.url}/simple-state")
public class StateBasicController extends BasicController<State, UUID> {

    @Autowired
    public StateBasicController(StateRepository repository) {
        super(repository);
    }
}
