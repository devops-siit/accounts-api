package com.dislinkt.accountsapi.web.rest.account.payload.request;

import com.dislinkt.accountsapi.domain.account.Gender;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EditProfileRequest {

    private String name;

    private String email;

    private Gender gender;

    private LocalDateTime dateOfBirth;

    private String biography;

    private Boolean isPublic;
}
