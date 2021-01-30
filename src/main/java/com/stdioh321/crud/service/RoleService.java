package com.stdioh321.crud.service;

import com.stdioh321.crud.model.Role;
import com.stdioh321.crud.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleService extends GenericService<Role, String> {
    public RoleService(RoleRepository repository) {
        super(repository);
    }
}
