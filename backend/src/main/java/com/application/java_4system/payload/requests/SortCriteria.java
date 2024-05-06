package com.application.java_4system.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
@AllArgsConstructor
public class SortCriteria{
    private String fieldToSort;
    private Direction sortDirection;
}