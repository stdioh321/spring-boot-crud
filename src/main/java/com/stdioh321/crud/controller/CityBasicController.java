package com.stdioh321.crud.controller;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.url}/city")
public class CityBasicController extends BasicController<City, UUID> {

    @Autowired
    public CityBasicController(CityRepository repository) {
        super(repository);
    }
}
