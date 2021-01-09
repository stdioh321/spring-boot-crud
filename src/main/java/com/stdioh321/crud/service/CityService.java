package com.stdioh321.crud.service;

import com.stdioh321.crud.model.City;
import com.stdioh321.crud.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public City post(City city){
        return cityRepository.saveAndFlush(city);
    }
    public List<City> getAll(){
        return cityRepository.findAll();
    }
    public City getById(UUID id){
        return cityRepository.findById(id).orElse(null);
    }
}
