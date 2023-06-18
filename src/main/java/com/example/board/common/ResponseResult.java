package com.example.board.common;

import org.springframework.http.ResponseEntity;

public final class ResponseResult {
    public static ResponseEntity<?> result(ServiceResult result) {
        return new ResponseEntity<>(result.getData(), result.getStatus());
    }
}
