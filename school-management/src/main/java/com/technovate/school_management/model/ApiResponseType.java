package com.technovate.school_management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseType<T> {
    private boolean status;
    private T data;
    private String message;

    public static <T> ApiResponseType<T> generateSuccessResponse(T data) {
        return new ApiResponseType<>(true, data, "Success");
    }

    public static <T> ApiResponseType<T> generateSuccessResponse(T data, String message) {
        return new ApiResponseType<>(true, data, message);
    }

    public static <T> ApiResponseType<T> generateFailureResponse(T data) {
        return new ApiResponseType<>(false, data, "Failed");
    }

    public static <T> ApiResponseType<T> generateFailureResponse(T data, String message) {
        return new ApiResponseType<>(false, data, message);
    }
}
