package com.sparos4th2.alarm.common.exception;

import com.sparos4th2.alarm.common.exception.ResponseStatus;

public class CustomException extends RuntimeException {

    private final ResponseStatus responseStatus;

    public CustomException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

}
