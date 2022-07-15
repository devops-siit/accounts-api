package com.dislinkt.accountsapi.exception.types;

import com.dislinkt.accountsapi.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends BaseException {
    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistsException(String errorMessage) {
        super(errorMessage, HttpStatus.CONFLICT);
    }
}