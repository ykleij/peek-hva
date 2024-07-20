package com.example.backend.repositories;

import java.util.List;

public interface GenericRepository<E> {
    List<E> findAll();

    E findById(long id);

    E save(E entity);

    E deleteById(long id);
}
