package com.stdioh321.crud.service;

import com.stdioh321.crud.exception.EntityNotFoundException;
import com.stdioh321.crud.model.JwtCheckList;
import com.stdioh321.crud.repository.JwtCheckListRepository;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JwtCheckListService extends GenericService<JwtCheckList, UUID>{


    public JwtCheckListService(JwtCheckListRepository repository) {
        super(repository);
    }

    public boolean isBlacklisted(String jwt) {
        boolean isBlacklisted = ((JwtCheckListRepository) repository).isBlacklisted(jwt);
        return isBlacklisted;
    }
}
