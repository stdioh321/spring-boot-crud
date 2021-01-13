package com.stdioh321.crud.controller;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@RestController
@RequestMapping("${api.url}/simple-city")
public class CityBasicController extends BasicController<City, UUID> {

    @PersistenceContext(name = "db1EntityManager")
    private EntityManager em;


    @Autowired
    public CityBasicController(CityRepository repository) {
        super(repository);
    }
}
