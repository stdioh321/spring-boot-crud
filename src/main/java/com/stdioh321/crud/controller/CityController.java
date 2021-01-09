package com.stdioh321.crud.controller;

import com.stdioh321.crud.exception.EntityValidationException;
import com.stdioh321.crud.model.City;
import com.stdioh321.crud.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("${api.url}/city")

public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity getAll(){
        return new ResponseEntity(cityService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid City city,BindingResult result){
        if(result.hasErrors()){
            throw new EntityValidationException(result.getFieldErrors());
        }
        return ResponseEntity.ok(city);
    }
}
