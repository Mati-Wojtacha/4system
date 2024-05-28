package com.application.user_management.unit.converters;

import com.application.user_management.converters.BaseSortCriteriaConverter;
import com.application.user_management.payload.requests.SortCriteria;

import java.util.List;

public class FakeSortCriteriaConverter extends BaseSortCriteriaConverter {
    public List<SortCriteria> convert(String source) {
        return convertInternal(source);
    }
}