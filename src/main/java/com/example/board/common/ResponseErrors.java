package com.example.board.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseErrors {
    private String field;
    private String message;

    public static ResponseErrors of(FieldError fieldError) {
        return ResponseErrors.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }
}
