package com.stdioh321.crud.service;

import java.util.List;
import java.util.UUID;

public interface BasicService<T, ID> {
    public List<T> getAll();
    public T getById(ID id);
    public T post(T entity);
    public T delete(ID id);
    public T put(ID id, T entity);
    public Long count();
}
