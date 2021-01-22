package com.stdioh321.crud.controller.api;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.repository.CityRepository;
import com.stdioh321.crud.utils.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.url}" + Routes.CITY)

public class CityBasicControllerWithRepository extends BasicControllerWithRepository<City, UUID> {

    @Autowired
    public CityBasicControllerWithRepository(CityRepository repository) {
        super(repository);
    }

}
