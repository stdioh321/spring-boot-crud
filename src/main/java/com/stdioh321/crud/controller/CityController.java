package com.stdioh321.crud.controller;

import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.exception.EntityValidationException;
import com.stdioh321.crud.model.City;
import com.stdioh321.crud.service.CityService;
import com.stdioh321.crud.service.TmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("${api.url}/city")

public class CityController {
    @Autowired
    private CityService cityService;

    @Autowired
    private TmpService tmpService;

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") UUID id) {
        return new ResponseEntity(cityService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll(@PathParam("q") String q, @PathParam("fields") String fields) {
        List<?> all = null;
        try {
            all = tmpService.getByFieldQuery(fields, q);
        } catch (Exception e) {
            throw new RestGenericExecption("Unable to get Entities", HttpStatus.INTERNAL_SERVER_ERROR, null, City.class.getSimpleName());
        }

        return new ResponseEntity(all, HttpStatus.OK);
        /*return new ResponseEntity(tmpService.getAll(), HttpStatus.OK);*/
        /*return new ResponseEntity(cityService.getByFieldQuery(q, fields), HttpStatus.OK);*/
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid City city, BindingResult result) {
        if (result.hasErrors()) {
            throw new EntityValidationException(result.getFieldErrors());
        }
        return ResponseEntity.ok(city);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(cityService.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") UUID id, @RequestBody City city) {
        return ResponseEntity.ok(cityService.put(id, city));
    }
}
