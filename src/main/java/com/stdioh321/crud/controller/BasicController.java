package com.stdioh321.crud.controller;

import com.stdioh321.crud.exception.DataPersistenceGenericException;
import com.stdioh321.crud.exception.EntityNotFoundException;
import com.stdioh321.crud.exception.EntityValidationException;
import com.stdioh321.crud.model.BasicModel;
import com.stdioh321.crud.utils.IRepositoryExtender;
import com.stdioh321.crud.utils.Utils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public abstract class BasicController<ENT extends BasicModel, ID> {
    private IRepositoryExtender<ENT, ID> repository;

    public BasicController(IRepositoryExtender repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") ID id) {
        var city = repository.findById(id);
        if (city.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(city.get());
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid ENT entity, BindingResult result) {
        if (result.hasErrors()) {
            throw new EntityValidationException(result.getFieldErrors());
        }
        ENT e;
        try {
            e = repository.saveAndFlush(entity);
        } catch (Exception exc) {
            throw new DataPersistenceGenericException(exc.getMessage(), entity.toString(), entity.getClass().getName());
        }
        return ResponseEntity.ok(e);
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") ID id, @RequestBody @Valid ENT entity, BindingResult result) throws NoSuchFieldException, IllegalAccessException {
        if (result.hasErrors()) {
            throw new EntityValidationException("Validation Error", result.getFieldErrors());
        }
        var currentEntity = repository.findById(id);
        if (currentEntity.isEmpty()) throw new EntityNotFoundException(id.toString(), entity.getClass().getName());


        var newEntity = Utils.mergeObjects(currentEntity.get(), entity);
        ENT e;
        try {
            e = repository.saveAndFlush(newEntity);
        } catch (Exception exc) {
            throw new DataPersistenceGenericException(exc.getMessage(), entity.toString(), entity.getClass().getName());
        }
        return ResponseEntity.ok(e);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") ID id) {
        var currentEntity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), null));
        repository.delete(currentEntity);
        repository.flush();
        /*
        currentEntity.setDeletedAt(new Date());
        repository.saveAndFlush(currentEntity);
        */
        return ResponseEntity.ok(currentEntity);

    }

    @GetMapping("/deleted")
    public ResponseEntity getTrashed(){
        return ResponseEntity.ok(repository.findAll());
    }
}

