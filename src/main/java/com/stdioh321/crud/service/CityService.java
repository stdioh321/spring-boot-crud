package com.stdioh321.crud.service;

import com.stdioh321.crud.exception.RestNotFoundException;
import com.stdioh321.crud.model.City;
import com.stdioh321.crud.repository.CityRepository;
import com.stdioh321.crud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CityService implements BasicService<City, UUID> {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public City post(City city) {
        return cityRepository.saveAndFlush(city);
    }

    @Override
    public City delete(UUID id) {
        var tmpCity = cityRepository.findById(id);
        if (tmpCity.isEmpty()) {
            throw new RestNotFoundException(id.toString(), City.class);
        }
        cityRepository.delete(tmpCity.get());
        cityRepository.flush();
        return tmpCity.get();
    }

    @Override
    public City put(UUID id, City entity) {
        var tmpCity = cityRepository.findById(id);
        if (tmpCity.isEmpty()) {
            throw new RestNotFoundException(id.toString(), City.class);
        }
        var currCity = tmpCity.get();
         try {
             currCity = Utils.mergeObjects(tmpCity.get(), entity);
         }catch (Exception e){

         }

        return cityRepository.saveAndFlush(currCity);
    }

    @Override
    public Long count() {
        return cityRepository.count();
    }


    public List<City> getByFieldQuery(String q, String fields) {
        return cityRepository.findByFieldQuery(fields, q, null);
    }


    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public City getById(UUID id) {
        var tmpCity = cityRepository.findById(id);
        if (tmpCity.isEmpty())
            throw new RestNotFoundException(id.toString(), City.class);
        return tmpCity.get();
    }
}
