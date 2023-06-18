package com.example.board.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public final class ServiceResult {
    private Object     data;
    private HttpStatus status;

    public static ServiceResult fail(String message) {
        return ServiceResult.builder()
                .data(message)
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    public static ServiceResult success(Object data) {
        return ServiceResult.builder()
                .data(data)
                .status(HttpStatus.OK)
                .build();
    }

    public static ServiceResult success() {
        return ServiceResult.success(null);
    }
}
