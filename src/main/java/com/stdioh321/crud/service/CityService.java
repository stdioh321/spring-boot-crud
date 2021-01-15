package com.stdioh321.crud.service;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.repository.CityRepository;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CityService extends GenericService<City, UUID> {
    @Autowired
    public CityService(CityRepository repository) {
        super(repository);
    }
}
