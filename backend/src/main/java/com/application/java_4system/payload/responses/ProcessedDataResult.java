package com.application.java_4system.payload.responses;

import com.application.java_4system.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedDataResult {
    private int totalObjects;

    private int totalProcessedObjects;

    private int nullableObjects;

    private List<User> incompleteObjects;

    private ResponseError error;

    public ProcessedDataResult(ResponseError error) {
        this.error = error;
    }
}