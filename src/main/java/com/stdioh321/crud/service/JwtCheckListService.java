package com.stdioh321.crud.service;

import com.stdioh321.crud.model.JwtCheckList;
import com.stdioh321.crud.repository.JwtCheckListRepository;
import org.springframework.stereotype.Service;

@Service
public class JwtCheckListService extends GenericService<JwtCheckList, String>{


    public JwtCheckListService(JwtCheckListRepository repository) {
        super(repository);
    }

    public boolean isBlacklisted(String jwt) {
        boolean isBlacklisted = ((JwtCheckListRepository) repository).isBlacklisted(jwt);
        return isBlacklisted;
    }
}
