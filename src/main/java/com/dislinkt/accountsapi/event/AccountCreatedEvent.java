package com.dislinkt.accountsapi.event;

import com.dislinkt.accountsapi.domain.account.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountCreatedEvent {

    private String uuid;

    private String username;

    private String name;

    private String email;

    private String phone;

    private Gender gender;

    private LocalDateTime dateOfBirth;
}