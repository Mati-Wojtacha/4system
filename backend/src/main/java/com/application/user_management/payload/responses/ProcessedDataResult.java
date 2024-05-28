package com.application.user_management.payload.responses;

import com.application.user_management.models.User;
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