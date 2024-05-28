package com.application.crud;

import com.application.user_management.models.BaseEntity;
import com.application.user_management.payload.requests.SearchSortPaginationConfig;
import com.application.user_management.payload.requests.SortCriteria;
import com.application.user_management.payload.responses.PagingSortResponse;
import com.application.user_management.repository.SearchSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class CrudServiceBase<T extends BaseEntity, ID, R extends JpaRepository<T, ID> & JpaSpecificationExecutor<T>> implements CrudService<T, ID> {
    protected final R repository;
    private final Logger logger;

    public CrudServiceBase(R repository) {
        this.repository = repository;
        logger = LoggerFactory.getLogger(CrudServiceBase.class);
    }

    protected abstract Collection<String> getColumnNames();

    public List<T> list() {
        return repository.findAll();
    }

    public PagingSortResponse<T> list(SearchSortPaginationConfig searchSortPaginationConfig) {
        var pageRequest = PageRequest.of(searchSortPaginationConfig.getPage(), searchSortPaginationConfig.getSize());

        Specification<T> customSpecifications = null;
        if (searchSortPaginationConfig.getSearchTerm() != null) {
            customSpecifications = SearchSpecifications.searchInMultipleColumns(searchSortPaginationConfig.getSearchTerm(), getColumnNames());
        }

        if (searchSortPaginationConfig.getSortCriteria() == null || searchSortPaginationConfig.getSortCriteria().isEmpty()) {
            return new PagingSortResponse<>(repository.findAll(Specification.where(customSpecifications), pageRequest));
        }

        var validFieldsInUser = new ArrayList<SortCriteria>();
        for (var criteria : searchSortPaginationConfig.getSortCriteria()) {
            try {
                validFieldsInUser.add(new SortCriteria(criteria.getFieldToSort(), criteria.getSortDirection()));
            } catch (Exception e) {
                logger.error("Invalid field name: {}", criteria);
            }
        }
        var sort = Sort.by(validFieldsInUser.stream()
                .map(criteria -> new Sort.Order(criteria.getSortDirection(), criteria.getFieldToSort()))
                .toList());

        return new PagingSortResponse<>(repository.findAll(Specification.where(customSpecifications), pageRequest.withSort(sort)));
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public T update(T newEntity) {

        return findById((ID) newEntity.getId())
                .map(entity -> {
                    entity = newEntity;

                    return repository.save(entity);
                })
                .orElse(null);
    }

    public boolean deleteById(ID id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                })
                .orElse(false);
    }
}