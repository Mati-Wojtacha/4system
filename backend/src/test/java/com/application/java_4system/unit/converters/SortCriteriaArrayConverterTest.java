package com.application.java_4system.unit.converters;

import com.application.java_4system.converters.SortCriteriaArrayConverter;
import com.application.java_4system.payload.requests.SortCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SortCriteriaArrayConverterTest {
    private final SortCriteriaArrayConverter arrayConverter = new SortCriteriaArrayConverter();

    @Test
    void convert_ShouldReturnSortCriteriaList_WhenSourceIsCorrect() {
        String[] source = {"name:asc", "login:desc"};
        List<SortCriteria> result = arrayConverter.convert(source);

        assertNotNull(result);

        assertEquals(result.size(), 2);

        SortCriteria firstCriteria = result.getFirst();
        assertEquals(firstCriteria.getFieldToSort(), "name");
        assertEquals(firstCriteria.getSortDirection(), Sort.Direction.ASC);

        SortCriteria secondCriteria = result.getLast();
        assertEquals(secondCriteria.getFieldToSort(), "login");
        assertEquals(secondCriteria.getSortDirection(), Sort.Direction.DESC);
    }

    @Test
    void convert_ShouldReturnEmptyList_WhenSourceIsIncorrect() {
        String[] source = {"name", "login"};
        List<SortCriteria> result = arrayConverter.convert(source);

        assertNotNull(result);
        assertEquals(result.size(), 0);
    }
}