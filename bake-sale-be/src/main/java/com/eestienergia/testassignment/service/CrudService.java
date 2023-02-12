package com.eestienergia.testassignment.service;

import java.util.List;
import java.util.UUID;

public interface CrudService<T> {
    List<T> getAll();

    T get(UUID id);

    T insert(T t);

    T update(T t);

    void delete(UUID id);
}
