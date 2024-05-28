package com.application.crud;

import com.application.user_management.payload.requests.SearchSortPaginationConfig;
import com.application.user_management.payload.responses.PagingSortResponse;

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