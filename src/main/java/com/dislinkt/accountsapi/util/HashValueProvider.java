package com.dislinkt.accountsapi.util;

import java.util.UUID;

public class HashValueProvider {

    public static String generateHash() {
        return UUID.randomUUID().toString();
    }
}