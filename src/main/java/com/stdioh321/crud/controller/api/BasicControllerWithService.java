package com.stdioh321.crud.controller.api;

import com.stdioh321.crud.exception.DataPersistenceGenericException;
import com.stdioh321.crud.exception.EntityValidationException;
import com.stdioh321.crud.model.BasicModel;
import com.stdioh321.crud.service.IBasicService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;

public abstract class BasicControllerWithService<ENT extends BasicModel, ID> {
    private IBasicService<ENT, ID> service;
    protected Class<ENT> clazz = (Class<ENT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public BasicControllerWithService(IBasicService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(service.getAll());

    }

    @GetMapping("/{id}")

    public ResponseEntity<ENT> getById(@PathVariable("id") ID id) {
        var entity = service.getById(id);
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid ENT entity, BindingResult result) {
        if (result.hasErrors()) {
            throw new EntityValidationException(result.getFieldErrors());
        }
        ENT e;
        try {
            e = service.post(entity);
        } catch (Exception exc) {
            throw new DataPersistenceGenericException(exc.getMessage(), entity.toString(), entity.getClass().getName());
        }
        return ResponseEntity.ok(e);
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable("id") ID id, @RequestBody @Valid ENT entity, BindingResult result) throws NoSuchFieldException, IllegalAccessException {
        if (result.hasErrors()) {
            throw new EntityValidationException(result.getFieldErrors());
        }
        var newEntity = service.put(id, entity);

        return ResponseEntity.ok(newEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") ID id) {
        var currentEntity = service.delete(id);
        return ResponseEntity.ok(currentEntity);

    }

    @GetMapping("/deleted")
    public ResponseEntity getTrashed() {
        return ResponseEntity.ok(service.getTrashed());
    }

    @GetMapping("/deleted/{id}")
    public ResponseEntity getTrashedById(@PathVariable("id") ID id) {
        var entTrashed = service.getTrashedById(id);
        return ResponseEntity.ok(entTrashed);
    }

    @GetMapping(value = "/restore/{id}")
    public ResponseEntity restoreTrashedById(@PathVariable(value = "id") ID id) {
        var restored = service.restore(id);
        return ResponseEntity.ok(restored);
    }

    @GetMapping(value = "/tmp/{id}")
    public ResponseEntity getTmp(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(id);

    }
}

