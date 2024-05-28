package com.application.user_management.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseError {
    private String message;

    private EnumMessage enumMessage;

    public enum EnumMessage{
        VALIDATION_ERROR_OR_UNSUPPORTED_FILE,
        EMPTY_FILE
    }
}