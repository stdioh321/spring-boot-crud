package com.stdioh321.crud.service;

import com.stdioh321.crud.exception.EntityGenericExecption;
import com.stdioh321.crud.model.City;
import com.stdioh321.crud.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            throw new EntityGenericExecption("City Not Found", id.toString(), City.class.getSimpleName(), HttpStatus.NOT_FOUND);
        }
        cityRepository.delete(tmpCity.get());
        cityRepository.flush();
        return tmpCity.get();
    }

    @Override
    public City put(UUID id, City entity) {
        var tmpCity = cityRepository.findById(id);
        if(tmpCity.isEmpty()){
            throw new EntityGenericExecption("City not Found", id.toString(), City.class.getSimpleName(), HttpStatus.NOT_FOUND);
        }
        var currCity = tmpCity.get();
        currCity.updateOnlyNotNull(entity,null);

        return cityRepository.saveAndFlush(currCity);
    }

    @Override
    public Long count() {
        return cityRepository.count();
    }



    public List<City> getByFieldQuery(String q, String fields) {
        return cityRepository.findByFieldQuery(fields,q,null);
    }


    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public City getById(UUID id) {
        var tmpCity = cityRepository.findById(id);
        if (tmpCity.isEmpty())
            throw new EntityGenericExecption("City not Found", id.toString(), City.class.getSimpleName(), HttpStatus.NOT_FOUND);
        return tmpCity.get();
    }
}
