package com.dislinkt.accountsapi.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreatedEvent {

    private String uuid;

    private String username;

    private String name;
}