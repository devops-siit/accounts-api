package com.dislinkt.accountsapi.exception.types;

import com.dislinkt.accountsapi.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseException {
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage, HttpStatus.NOT_FOUND);
    }
}