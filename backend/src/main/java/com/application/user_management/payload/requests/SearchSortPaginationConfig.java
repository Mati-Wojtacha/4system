package com.application.user_management.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchSortPaginationConfig {
    private int page = 0;
    private int size = 1;
    private String searchTerm;
    private List<SortCriteria> sortCriteria;
}