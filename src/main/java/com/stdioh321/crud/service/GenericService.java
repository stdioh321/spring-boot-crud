package com.stdioh321.crud.service;

import com.stdioh321.crud.exception.EntityNotFoundException;
import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.exception.RestNotFoundException;
import com.stdioh321.crud.model.BasicModel;
import com.stdioh321.crud.utils.IRepositoryExtender;
import com.stdioh321.crud.utils.Utils;
import org.springframework.http.HttpStatus;

import javax.persistence.MappedSuperclass;
import java.util.List;


@MappedSuperclass
public abstract class GenericService<T extends BasicModel, ID> implements IBasicService<T, ID> {

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
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), null));
    }

    @Override
    public T post(T entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public T delete(ID id) {
        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), null));
        repository.delete(entity);
        repository.flush();
        return entity;
    }

    @Override
    public T put(ID id, T entity) {
        var tmpEntity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), entity.getClass().getName()));
        try {
            tmpEntity = Utils.mergeObjects(tmpEntity, entity);
            repository.saveAndFlush(tmpEntity);
        } catch (Exception e) {
            throw new RestGenericExecption("Unable to merge entities", HttpStatus.UNPROCESSABLE_ENTITY, entity.toString(), null);
        }
        return tmpEntity;
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public List<T> getTrashed() {
        return repository.findTrashed();
    }

    @Override
    public T getTrashedById(ID id) {
        var entity = repository.findTrashedById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        return entity;
    }


    @Override
    public T restore(ID id) {
        var entity = repository.restore(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        return entity;
    }
}
