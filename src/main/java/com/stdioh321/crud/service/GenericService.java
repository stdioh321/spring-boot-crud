package com.stdioh321.crud.service;

import com.stdioh321.crud.exception.EntityNotFoundException;
import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.exception.RestNotFoundException;
import com.stdioh321.crud.model.BasicModel;
import com.stdioh321.crud.utils.IRepositoryExtender;
import com.stdioh321.crud.utils.Utils;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import javax.persistence.MappedSuperclass;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;


@MappedSuperclass
public abstract class GenericService<T extends BasicModel, ID> implements IBasicService<T, ID> {

    /*protected IRepositoryExtender<T, ID> repository;*/
    protected IRepositoryExtender<T, ID> repository;


    public GenericService(IRepositoryExtender repository) {
        this.repository = repository;
    }


    public List<?> getByFieldQuery(String field, String q) {
        return repository.findByFieldQuery(field, q, null);
    }

    @Override
    public List<T> getAll() {
        return repository.findAllActive();
    }

    @Override
    public Page<T> getPaginated(Integer page, Integer size) {
        page = Objects.isNull(page) ? 0 : page;
        size = Objects.isNull(size) ? 10 : size;
        var pageRequest = PageRequest.of(page, size);
        return repository.findPaginateActive(pageRequest);
    }


    @Override
    public T getById(ID id) {
        return repository.findByIdActive(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), null));
    }

    @Override
    public T post(T entity) {
        return repository.save(entity);
    }

    @Override
    public T delete(ID id) {
        var entity = repository.deleteSoft(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), null));
        return entity;
    }

    @Override
    public T put(ID id, T entity) {
        var tmpEntity = repository.findByIdActive(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), entity.getClass().getName()));
        try {
            tmpEntity = Utils.mergeObjects(tmpEntity, entity);
            repository.save(tmpEntity);
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
