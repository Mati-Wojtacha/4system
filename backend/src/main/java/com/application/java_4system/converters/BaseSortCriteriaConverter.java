package com.application.java_4system.converters;

import com.application.java_4system.payload.requests.SortCriteria;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseSortCriteriaConverter {
    protected List<SortCriteria> convertInternal(String source) {
        List<SortCriteria> criteriaList = new ArrayList<>();
        String[] partsOfCriteria = source.split(":");
        if (partsOfCriteria.length > 1) {
            Optional<Sort.Direction> direction = Sort.Direction.fromOptionalString(partsOfCriteria[1]);
            direction.ifPresent(value -> criteriaList.add(new SortCriteria(partsOfCriteria[0], value)));
        }
        return criteriaList;
    }
}