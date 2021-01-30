package com.stdioh321.crud.service;

import com.stdioh321.crud.model.State;
import com.stdioh321.crud.repository.StateRepository;
import org.springframework.stereotype.Service;

@Service
public class StateService extends GenericService<State, String> {
    public StateService(StateRepository repository) {
        super(repository);
    }
}
