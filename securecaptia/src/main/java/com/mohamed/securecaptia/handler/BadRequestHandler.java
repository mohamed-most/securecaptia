package com.mohamed.securecaptia.handler;

import com.mohamed.securecaptia.dto.ApiResponse;
import com.mohamed.securecaptia.exception.BadRequestException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>>handleBadRequestEx(BadRequestException exception){
        return
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(
                                new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), exception.getMessage() , null)
                        );
    }

}
