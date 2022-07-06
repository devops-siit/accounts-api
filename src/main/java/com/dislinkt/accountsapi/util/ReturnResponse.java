package com.dislinkt.accountsapi.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ReturnResponse {

    public static <T> ResponseEntity<T> entityGet(T obj) {
        return new ResponseEntity<T>(obj, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> entityCreated(T obj) {
        return new ResponseEntity<T>(obj, HttpStatus.CREATED);
    }

    public static ResponseEntity entityCreated() {
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> entityUpdated(T obj) {
        return new ResponseEntity<T>(obj, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> entityNotFound() {
        return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
    }
}