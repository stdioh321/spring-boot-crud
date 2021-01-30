package com.stdioh321.crud.repository;

import com.stdioh321.crud.model.State;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface StateCommonRepository extends JpaRepository<State, String> {}
