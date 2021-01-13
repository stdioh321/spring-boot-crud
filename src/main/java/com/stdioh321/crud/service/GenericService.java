package com.stdioh321.crud.service;

import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.exception.RestNotFoundException;
import com.stdioh321.crud.utils.IRepositoryExtender;
import com.stdioh321.crud.utils.Utils;
import org.springframework.http.HttpStatus;

import javax.persistence.MappedSuperclass;
import java.util.List;


@MappedSuperclass
public abstract class GenericService<T, ID> implements BasicService<T, ID> {

    private IRepositoryExtender<T, ID> repository;


    public GenericService(IRepositoryExtender repository) {
        this.repository = repository;
    }


    public List<?> getByFieldQuery(String field, String q) {
        return repository.findByFieldQuery(field, q, null);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T getById(ID id) {
        return repository.findById(id).orElseThrow(() -> new RestNotFoundException(null, id.toString()));
    }

    @Override
    public T post(T entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public T delete(ID id) {
        var entity = repository.findById(id).orElseThrow(() -> new RestNotFoundException(null, id.toString()));
        repository.delete(entity);
        repository.flush();
        return entity;
    }

    @Override
    public T put(ID id, T entity) {
        var tmpEntity = repository.findById(id).orElseThrow(() -> new RestNotFoundException(null, id.toString()));
        try {
            tmpEntity = Utils.mergeObjects(tmpEntity, entity);
        } catch (Exception e) {
            throw new RestGenericExecption("Unable to merge entities", HttpStatus.UNPROCESSABLE_ENTITY, entity.toString(), null);
        }
        return tmpEntity;
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
