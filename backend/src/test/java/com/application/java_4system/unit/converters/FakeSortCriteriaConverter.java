package com.application.java_4system.unit.converters;

import com.application.java_4system.converters.BaseSortCriteriaConverter;
import com.application.java_4system.payload.requests.SortCriteria;

import java.util.List;

public class FakeSortCriteriaConverter extends BaseSortCriteriaConverter {
    public List<SortCriteria> convert(String source) {
        return convertInternal(source);
    }
}