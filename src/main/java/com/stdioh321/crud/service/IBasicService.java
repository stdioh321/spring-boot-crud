package com.stdioh321.crud.service;

import com.stdioh321.crud.utils.IRepositoryExtender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface IBasicService<T, ID> {
    IRepositoryExtender repository = null;

    public List<T> getAll();
    public Page<T> getPaginated(Integer page, Integer size);

    public T getById(ID id);

    public T post(T entity);

    public T delete(ID id);

    public T put(ID id, T entity);

    public List<T> getTrashed();

    public T getTrashedById(ID id);

    public T restore(ID id);

    public Long count();

}
