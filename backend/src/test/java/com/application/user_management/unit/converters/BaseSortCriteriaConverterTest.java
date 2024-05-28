package com.application.user_management.unit.converters;

import com.application.user_management.payload.requests.SortCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BaseSortCriteriaConverterTest {
    private final FakeSortCriteriaConverter converter = new FakeSortCriteriaConverter();

    @Test
    void convertInternal_ShouldReturnSortCriteriaList_WhenSourceIsCorrect() {
        String source = "name:asc";
        List<SortCriteria> result = converter.convert(source);

        assertNotNull(result);
        assertEquals(result.size(), 1);

        SortCriteria criteria = result.getFirst();
        assertEquals(criteria.getFieldToSort(),"name");
        assertEquals(criteria.getSortDirection(), Sort.Direction.ASC);
    }

    @Test
    void convertInternal_ShouldReturnEmptyList_WhenSourceIsIncorrect() {
        String source = "name";
        List<SortCriteria> result = converter.convert(source);

        assertNotNull(result);
        assertEquals(result.size(), 0);
    }
}