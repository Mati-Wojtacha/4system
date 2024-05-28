package com.application.user_management.converters;

import com.application.user_management.payload.requests.SortCriteria;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class SortCriteriaArrayConverter extends BaseSortCriteriaConverter implements Converter<String[], List<SortCriteria>> {

    @Override
    public List<SortCriteria> convert(String[] source) {
        List<SortCriteria> criteriaList = new ArrayList<>();
        for (String criteria : source) {
            criteriaList.addAll(convertInternal(criteria));
        }

        return criteriaList;
    }
}