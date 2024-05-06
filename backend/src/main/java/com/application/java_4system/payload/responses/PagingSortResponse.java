package com.application.java_4system.payload.responses;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
public class PagingSortResponse<T> {
    private List<T> data;
    private Long total;

    public PagingSortResponse(Page<T> page){
        if(page == null){
            return;
        }
        data = page.stream().toList();
        total = page.getTotalElements();
    }
}