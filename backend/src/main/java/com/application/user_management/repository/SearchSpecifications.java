package com.application.user_management.repository;

import com.application.user_management.models.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.Collection;

public class SearchSpecifications {
    public static  <T extends BaseEntity> Specification<T> searchInMultipleColumns(String searchTerm, Collection<String> columnNames) {
        return (root, query, cb) -> {
            String containsLikePattern = "%" + searchTerm + "%";
            Collection<Predicate> predicates = new ArrayList<>();
            for (String columnName : columnNames) {
                predicates.add(cb.like(cb.lower(root.get(columnName)), containsLikePattern.toLowerCase()));
            }
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}