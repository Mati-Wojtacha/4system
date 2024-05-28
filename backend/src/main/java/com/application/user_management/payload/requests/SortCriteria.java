package com.application.user_management.payload.requests;

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