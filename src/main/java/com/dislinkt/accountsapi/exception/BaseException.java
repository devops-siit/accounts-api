package com.dislinkt.accountsapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {

    private HttpStatus status;

    public BaseException(String errorMessage, HttpStatus status) {
        super(errorMessage);
        this.status = status;
    }
}