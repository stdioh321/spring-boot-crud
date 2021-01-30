package com.stdioh321.crud.service;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TmpService extends GenericService<City, String> {
    @Autowired
    public TmpService(CityRepository repository) {
        super(repository);
    }
}
