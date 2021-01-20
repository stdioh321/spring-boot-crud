package com.stdioh321.crud.repository;

import com.stdioh321.crud.model.Role;
import com.stdioh321.crud.model.State;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends IRepositoryExtender<Role, UUID> {
}
