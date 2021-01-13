package com.stdioh321.crud.service;

import com.stdioh321.crud.utils.IRepositoryExtender;

import java.util.List;
import java.util.UUID;

public interface BasicService<T, ID> {
    IRepositoryExtender repository = null;
    public List<T> getAll();
    public T getById(ID id);
    public T post(T entity);
    public T delete(ID id);
    public T put(ID id, T entity);
    public Long count();

}
