package com.stdioh321.crud.repository;

import com.stdioh321.crud.model.State;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface StateRepository extends IRepositoryExtender <State, UUID> {}
