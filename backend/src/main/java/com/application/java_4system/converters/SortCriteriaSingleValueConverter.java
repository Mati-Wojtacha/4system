package com.application.java_4system.converters;

import com.application.java_4system.payload.requests.SortCriteria;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SortCriteriaSingleValueConverter extends BaseSortCriteriaConverter implements Converter<String, List<SortCriteria>> {

    @Override
    public List<SortCriteria> convert(String source) {
        return convertInternal(source);
    }
}