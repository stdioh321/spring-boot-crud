package com.stdioh321.crud.controller.api;

import com.stdioh321.crud.config.jwt.JwtTokenUtil;
import com.stdioh321.crud.exception.ApiError;
import com.stdioh321.crud.exception.DataPersistenceGenericException;
import com.stdioh321.crud.exception.EntityNotFoundException;
import com.stdioh321.crud.exception.EntityValidationException;
import com.stdioh321.crud.model.BasicModel;
import com.stdioh321.crud.service.IBasicService;
import com.stdioh321.crud.utils.IRepositoryExtender;
import com.stdioh321.crud.utils.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasicControllerWithRepository<ENT extends BasicModel, ID> {
    private IRepositoryExtender<ENT, ID> repository;

    public BasicControllerWithRepository(IRepositoryExtender repository) {
        this.repository = repository;
    }


    @GetMapping
    public ResponseEntity<List<ENT>> getAll() {
        
        return ResponseEntity.ok(repository.findAllActive());

    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "Not 200", content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<ENT> getById(
            @Parameter(example = "38cc3745-84da-4317-8240-547ab9806977")
            @PathVariable("id") ID id) {
        var entity = repository.findByIdActive(id);
        if (entity.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(entity.get());
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid ENT entity, BindingResult result) {
        if (result.hasErrors()) {
            throw new EntityValidationException(result.getFieldErrors());
        }
        ENT e;
        try {
            e = repository.save(entity);
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
        var currentEntity = repository.findByIdActive(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), entity.getClass().getName()));

        var newEntity = Utils.mergeObjects(currentEntity, entity);
        ENT e;
        try {
            e = repository.save(newEntity);
        } catch (Exception exc) {
            throw new DataPersistenceGenericException(exc.getMessage(), entity.toString(), entity.getClass().getName());
        }
        return ResponseEntity.ok(e);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") ID id) {
        var currentEntity = repository.deleteSoft(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), null));
        /*
        currentEntity.setDeletedAt(new Date());
        repository.saveAndFlush(currentEntity);
        */
        return ResponseEntity.ok(currentEntity);

    }

    @GetMapping("/deleted")
    public ResponseEntity getTrashed() {
        return ResponseEntity.ok(repository.findTrashed());
    }

    @GetMapping("/deleted/{id}")
    public ResponseEntity getTrashedById(@PathVariable("id") ID id) {
        var entTrashed = repository.findTrashedById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), null));
        return ResponseEntity.ok(entTrashed);
    }

    @GetMapping(value = "/restore/{id}")
    public ResponseEntity restoreTrashedById(@PathVariable(value = "id") ID id) {
        var restored = repository.restore(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), null));
        return ResponseEntity.ok(restored);
    }

    @GetMapping(value = "/tmp/{id}")
    public ResponseEntity getTmp(@PathVariable(value = "id") ID id) {
        var optEntity = repository.findByIdActive(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), null));
        repository.delete(optEntity);
        return ResponseEntity.ok(optEntity);
    }
}

