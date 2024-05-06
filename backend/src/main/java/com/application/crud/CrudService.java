package com.application.crud;

import com.application.java_4system.payload.requests.SearchSortPaginationConfig;
import com.application.java_4system.payload.responses.PagingSortResponse;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    Optional<T> findById(ID id);

    T save(T entity);

    T update(T newEntity);

    boolean deleteById(ID id);

    List<T> list();

    PagingSortResponse<T> list(SearchSortPaginationConfig searchSortPaginationConfig);
}