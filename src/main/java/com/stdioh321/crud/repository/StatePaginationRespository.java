package com.stdioh321.crud.repository;

import com.stdioh321.crud.model.State;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface StatePaginationRespository extends PagingAndSortingRepository<State, UUID> {
}
