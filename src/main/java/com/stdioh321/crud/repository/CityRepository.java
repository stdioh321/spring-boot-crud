package com.stdioh321.crud.repository;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CityRepository extends IRepositoryExtender <City, String> {}
