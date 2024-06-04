package com.sparos4th2.alarm.common.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionResponse extends ResponseEntity<String> {
    @Builder
    public ExceptionResponse(ResponseStatus responseStatus) {
        super(responseStatus.getMessage(), HttpStatus.valueOf(responseStatus.getCode()));
    }
}
