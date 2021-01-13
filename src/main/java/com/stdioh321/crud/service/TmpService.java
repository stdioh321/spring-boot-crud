package com.stdioh321.crud.service;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.repository.CityRepository;
import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TmpService extends GenericService<City, UUID> {
    @Autowired
    public TmpService(CityRepository repository) {
        super(repository);
    }
}
